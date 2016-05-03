package org.ojacquemart.eurobets.firebase.initdb.raw

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.Before
import org.junit.Test

class RawFixturesJsonFileLoaderTest {

    private var loader: RawFixturesJsonFileLoader? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        loader = RawFixturesJsonFileLoader()
    }

    @Test
    @Throws(Exception::class)
    fun testLoad() {
        val rawFixtures = loader!!.load()

        assertThat(rawFixtures).isNotNull()

        val sheets = rawFixtures.sheets
        assertThat(sheets).isNotNull()
        assertFixtures(sheets)
        assertGroups(sheets)
    }

    private fun assertFixtures(sheets: Sheets) {
        val fixtures = sheets.fixtures
        assertThat(fixtures).isNotNull()

        val fixture1 = fixtures[0]
        assertThat(fixture1.id).isEqualTo(1)
        assertThat(fixture1.date).isEqualTo("10/06/2016")
        assertThat(fixture1.phase).isEqualTo("Group_A")
        assertThat(fixture1.homeTeam).isEqualTo("France")
        assertThat(fixture1.homeGoals).isEqualTo("")
        assertThat(fixture1.awayTeam).isEqualTo("Romania")
        assertThat(fixture1.awayGoals).isEqualTo("")
        assertThat(fixture1.resultFixture).isEqualTo("fixture")
        assertThat(fixture1.kickoffTimeLocal).isEqualTo("21:00")
    }

    private fun assertGroups(sheets: Sheets) {
        assertThat<RawGroupMember>(sheets.groupA).isNotNull()
        assertThat<RawGroupMember>(sheets.groupB).isNotNull()
        assertThat<RawGroupMember>(sheets.groupC).isNotNull()
        assertThat<RawGroupMember>(sheets.groupD).isNotNull()
        assertThat<RawGroupMember>(sheets.groupE).isNotNull()
        assertThat<RawGroupMember>(sheets.groupF).isNotNull()

        val groupA = sheets.groupA
        val groupMember1 = groupA[0]
        assertThat(groupMember1.country).isEqualTo("France")
        assertThat(groupMember1.position).isEqualTo("0")
        assertThat(groupMember1.points).isEqualTo("0")
        assertThat(groupMember1.win).isEqualTo("0")
        assertThat(groupMember1.lose).isEqualTo("0")
        assertThat(groupMember1.draw).isEqualTo("0")
        assertThat(groupMember1.goalsFor).isEqualTo("0")
        assertThat(groupMember1.goalsAgainst).isEqualTo("0")
    }

}
