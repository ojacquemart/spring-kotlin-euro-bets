package org.ojacquemart.eurobets.firebase.management.table

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.Test

class TableAssemblerTest {

    @Test
    fun testGetTable() {
        // take data from data source and add a clone of foo
        // foo should be first with its clone which does the same bets
        // it illustrates cases when many players should share the same step podium

        val fooCloned = DatasourceForTest.foo.copy(uid = "foo-clone", displayName = "Foo clone")
        val fooBets = DatasourceForTest.bets.filter { it.user!!.uid.equals("foo") }
                .map { it.copy(user = fooCloned) }
        val assembler = TableAssembler(DatasourceForTest.bets.plus(fooBets))
        val table = assembler.getTable()

        val rows = table.table
        assertThat(rows.size).isEqualTo(5)
        assertThat(rows).extracting("uid").contains("foo", "foo-clone", "baz", "bar", "qix")

        val podium = table.podium
        assertThat(podium.steps.size).isEqualTo(3)
        assertThat(podium.steps[1]!!.size).isEqualTo(2)
        assertThat(podium.steps[1]!!).extracting("uid").contains("foo", "foo-clone")
        assertThat(podium.steps[2]!!.size).isEqualTo(1)
        assertThat(podium.steps[3]!!.size).isEqualTo(1)
    }

}