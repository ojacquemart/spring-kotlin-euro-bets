package org.ojacquemart.eurobets.firebase.management.table

import org.ojacquemart.eurobets.lang.loggerFor

class TableAssembler(val bets: List<BetData>) {

    private val log = loggerFor<TableAssembler>()

    fun getTable(): Table {
        val finalTable = getTableRows()
        val podium = PodiumCreator(finalTable).create()

        return Table(table = finalTable, nbRows = finalTable.size, podium = podium)
    }

    private fun getTableRows(): List<TableRow> {
        val currentTable = TableCalculator(bets).getRows()

        val matchNumbersDesc = bets.map { it.match!!.number }.distinct()
        if (matchNumbersDesc.size > 1) return getTableRowsWithEvolution(currentTable, matchNumbersDesc)
        else {
            log.debug("No need to compute table positions evolution")
            return currentTable
        }
    }

    private fun getTableRowsWithEvolution(currentTable: List<TableRow>, matchNumbersDesc: List<Int>): List<TableRow> {
        val currentMatchNumber = matchNumbersDesc.last()

        log.debug("Compute table for match < $currentMatchNumber")
        val previousBets = bets.filter { it.match!!.number < currentMatchNumber }
        val previousTable = TableCalculator(previousBets).getRows()
        val previousTableByUid = previousTable.associateBy({ it.uid }, { it })

        return currentTable.map { currentTableRow ->
            val previousTableRowUid = previousTableByUid[currentTableRow.uid]
            if (previousTableRowUid == null) {
                log.trace("${currentTableRow.uid} is a new player...")
                currentTableRow
            } else {
                log.trace("${currentTableRow.uid} goes from ${previousTableRowUid.position} to ${currentTableRow.position}")
                currentTableRow.copy(evolution = previousTableRowUid.position - currentTableRow.position)
            }
        }
    }

}