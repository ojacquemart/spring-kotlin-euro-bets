package org.ojacquemart.eurobets.firebase.management.table

import kotlin.comparisons.compareBy
import kotlin.comparisons.thenBy

class TableCalculator(val bets: List<BetData>) {

    fun getRows(): List<TableRow> {
        val tableRows = getTableRows()
        val positions = TableRow.getPositions(tableRows)

        val rowsWithPosition = tableRows.map {
            val position = positions.indexOf(it.points) + 1
            val last10Recents = it.recents.takeLast(10).toTypedArray()

            it.copy(position = position, recents = last10Recents)
        }

        val comparator = compareBy<TableRow> { it.position }
            .thenBy { -it.bets }.thenBy { -it.perfects }.thenBy { -it.goods }
            .thenBy { it.displayName }

        return rowsWithPosition.sortedWith(comparator)
    }

    private fun getTableRows(): List<TableRow> {
        return bets.groupBy { bet -> bet.user }
                .map { entry ->
                    val user = entry.key!!
                    val bets = entry.value

                    bets.filter { it.user!!.uid.equals(user.uid) }
                }
                .map { bet -> reduce(bet) }
    }

    fun reduce(bets: List<BetData>): TableRow {
        return bets.map { bet ->
            val user = bet.user!!

            val resultPoints = bet.getResultPoints()
            val result = resultPoints?.result

            TableRow(
                    uid = user.uid, displayName = user.displayName, profileImageURL = user.profileImageURL,
                    points = resultPoints?.points ?: 0,
                    bets = if (result != null) 1 else 0,
                    perfects = if (result == Result.PERFECT) 1 else 0,
                    percentage = 0,
                    goods = if (result == Result.GOOD) 1 else 0,
                    bads = if (result == Result.BAD) 1 else 0,
                    recents = arrayOf(if (result != null) result.id else Result.UNDEFINED.id)
            )
        }.reduce { current, next ->
            current.copy(
                    points = current.points + next.points,
                    bets = current.bets + next.bets,
                    perfects = current.perfects + next.perfects,
                    percentage = TableRow.percentage(next, current),
                    goods = current.goods + next.goods,
                    bads = current.bads + next.bads,
                    recents = current.recents.plus(next.recents[0]))
        }
    }

}