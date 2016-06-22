package org.ojacquemart.eurobets.firebase.management.table

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.Test

class TableRowTest {

    val rows = listOf(
            // 30 points
            TableRow(bets = 10, points = 30, perfects = 0, goods = 10, bads = 0),
            TableRow(bets = 3, points = 30, perfects = 3, goods = 0, bads = 0),
            // 9 points
            TableRow(bets = 4, points = 9, perfects = 0, goods = 3, bads = 1),
            // 10 points
            TableRow(bets = 3, points = 10, perfects = 1, goods = 0, bads = 2),
            TableRow(bets = 4, points = 10, perfects = 1, goods = 0, bads = 3),
            // 0 point
            TableRow(bets = 0, points = 0, perfects = 0, goods = 0, bads = 0)
    )

    @Test
    fun testGetPositionCoefficient() {
        val rowsPositions = TableRow.getPositions(rows)

        assertThat(rowsPositions).hasSize(4)
        assertThat(rowsPositions[0]).isEqualTo(30)
        assertThat(rowsPositions[1]).isEqualTo(10)
        assertThat(rowsPositions[2]).isEqualTo(9)
        assertThat(rowsPositions[3]).isEqualTo(0)
    }

}