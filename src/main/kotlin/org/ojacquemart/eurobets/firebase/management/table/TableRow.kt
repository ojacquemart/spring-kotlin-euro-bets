package org.ojacquemart.eurobets.firebase.management.table

data class TableRow(val position: Int = 0,
                    val uid: String = "",
                    val displayName: String = "",
                    val profileImageURL: String = "",
                    val evolution: Int? = 0,
                    val points: Int = 0,
                    val bets: Int = 0,
                    val perfects: Int = 0,
                    val goods: Int = 0,
                    val bads: Int = 0,
                    val percentage: Int = 0,
                    val recents: Array<Int> = arrayOf()) {

    fun getPositionCoefficient(): Double {
        val coefficient = if (bets > 0) + ((perfects * 0.5 / bets) + (goods * 0.25 / bets)) else 0.0

        return points + coefficient
    }

    companion object {
        fun percentage(tableRow1: TableRow, tableRow2: TableRow): Int {
            val nbBets = tableRow1.bets + tableRow2.bets
            val sumOfPerfectOrGoodsBets = tableRow1.goods + tableRow1.perfects + tableRow2.goods + tableRow2.perfects

            return (sumOfPerfectOrGoodsBets.toFloat().div(nbBets) * 100).toInt();
        }

        fun getPositions(tableRows: List<TableRow>): List<Double> {
            return tableRows.groupBy { tableRow -> tableRow.getPositionCoefficient() }
                    .toList()
                    .map { pair -> pair.first }
                    .sortedDescending()
        }

    }
}
