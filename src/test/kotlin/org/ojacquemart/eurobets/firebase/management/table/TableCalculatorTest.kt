package org.ojacquemart.eurobets.firebase.management.table

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.Test

class TableCalculatorTest {

    @Test
    fun testGetTable() {
        val table = TableCalculator(DatasourceForTest.bets).getRows()

        assertThat(table).isNotEmpty()

        // foo #1
        val foo = findUserInTableRows("foo", table)
        assertThat(foo.position).isEqualTo(1)
        assertThat(foo.uid).isEqualTo("foo")
        assertThat(foo.displayName).isEqualTo("Foo")
        assertThat(foo.profileImageURL).isEqualTo("foo.png")
        assertThat(foo.points).isEqualTo(50)
        assertThat(foo.perfects).isEqualTo(5)
        assertThat(foo.goods).isEqualTo(0)
        assertThat(foo.bads).isEqualTo(0)
        assertThat(foo.bets).isEqualTo(5)
        assertThat(foo.percentage).isEqualTo(100)
        assertThat(foo.recents).isEqualTo(arrayOf(Result.PERFECT.id, Result.PERFECT.id, Result.PERFECT.id, Result.PERFECT.id, Result.PERFECT.id, Result.UNDEFINED.id, Result.UNDEFINED.id))

        // baz #2
        val baz = findUserInTableRows("baz", table)
        assertThat(baz.position).isEqualTo(2)
        assertThat(baz.uid).isEqualTo("baz")
        assertThat(baz.displayName).isEqualTo("Baz")
        assertThat(baz.profileImageURL).isEqualTo("baz.png")
        assertThat(baz.points).isEqualTo(50)
        assertThat(baz.perfects).isEqualTo(2)
        assertThat(baz.goods).isEqualTo(0)
        assertThat(baz.bads).isEqualTo(0)
        assertThat(baz.bets).isEqualTo(2)
        assertThat(baz.percentage).isEqualTo(100)
        assertThat(baz.recents).isEqualTo(arrayOf(Result.UNDEFINED.id, Result.UNDEFINED.id, Result.UNDEFINED.id, Result.UNDEFINED.id, Result.UNDEFINED.id, Result.PERFECT.id, Result.PERFECT.id))

        // bar #3
        val bar = findUserInTableRows("bar", table)
        assertThat(bar.position).isEqualTo(3)
        assertThat(bar.uid).isEqualTo("bar")
        assertThat(bar.displayName).isEqualTo("Bar")
        assertThat(bar.profileImageURL).isEqualTo("bar.png")
        assertThat(bar.points).isEqualTo(15)
        assertThat(bar.perfects).isEqualTo(0)
        assertThat(bar.goods).isEqualTo(5)
        assertThat(bar.bads).isEqualTo(0)
        assertThat(bar.bets).isEqualTo(5)
        assertThat(bar.percentage).isEqualTo(100)
        assertThat(bar.recents).isEqualTo(arrayOf(Result.GOOD.id, Result.GOOD.id, Result.GOOD.id, Result.GOOD.id, Result.GOOD.id, Result.UNDEFINED.id, Result.UNDEFINED.id))
    }

    fun findUserInTableRows(uid: String, tableRows: List<TableRow>): TableRow = tableRows.find { tableRow -> tableRow.uid.equals(uid) }!!
}