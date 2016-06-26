package org.ojacquemart.eurobets.firebase.management.table

import kotlin.comparisons.compareBy
import kotlin.comparisons.thenBy

class TableCalculator(val bets: List<BetData>, val recentsSize: Int = TableCalculator.RECENTS_SIZE) {

    fun getRows(): List<TableRow> {
        val tableRows = getTableRows()
        val positions = TableRow.getPositions(tableRows)

        val rowsWithPosition = tableRows
                .map {
                    val position = positions.indexOf(it.points) + 1
                    val last10Recents = it.recents.takeLast(recentsSize).toTypedArray()

                    it.copy(position = position, recents = last10Recents)
                }
                .filterNot {
                    val undefinedRecents = it.recents.filter { it == Result.UNDEFINED.id }

                    undefinedRecents.size == recentsSize
                }

        return rowsWithPosition.sortedWith(COMPARATOR)
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
                    percentage = 0,
                    perfects = if (result == Result.PERFECT) 1 else 0,
                    goodGaps = if (result == Result.GOOD_GAP) 1 else 0,
                    goods = if (result == Result.GOOD) 1 else 0,
                    bads = if (result == Result.BAD) 1 else 0,
                    recents = arrayOf(if (result != null) result.id else Result.UNDEFINED.id)
            )
        }.reduce { current, next ->
            current.copy(
                    points = current.points + next.points,
                    bets = current.bets + next.bets,
                    percentage = TableRow.percentage(next, current),
                    perfects = current.perfects + next.perfects,
                    goodGaps = current.goodGaps + next.goodGaps,
                    goods = current.goods + next.goods,
                    bads = current.bads + next.bads,
                    recents = current.recents.plus(next.recents[0]))
        }
    }

    companion object {

        val RECENTS_SIZE = 10

        val COMPARATOR = compareBy<TableRow> { it.position }
                .thenBy { -it.bets }.thenBy { -it.perfects }.thenBy { -it.goodGaps }.thenBy { -it.goods }
                .thenBy { it.displayName }
    }

}
