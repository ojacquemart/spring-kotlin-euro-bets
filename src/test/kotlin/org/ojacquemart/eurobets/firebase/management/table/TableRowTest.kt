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
        assertThat(rowsPositions[0]).isEqualTo(30.5)
        assertThat(rowsPositions[1]).isEqualTo(30.25)
        assertThat(rowsPositions[2]).isEqualTo(10.166666666666666)
        assertThat(rowsPositions[3]).isEqualTo(10.125)
        assertThat(rowsPositions[4]).isEqualTo(9.1875)
        assertThat(rowsPositions[5]).isEqualTo(0.0)
    }
}