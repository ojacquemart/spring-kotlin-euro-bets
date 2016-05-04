package org.ojacquemart.eurobets.firebase.initdb

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.Before
import org.junit.Test
import org.ojacquemart.eurobets.firebase.initdb.fixture.Status
import org.ojacquemart.eurobets.firebase.initdb.raw.RawFixturesJsonFileLoader

class SheetDataConverterTest {

    private var converter: SheetDataConverter? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        val rawFixtures = RawFixturesJsonFileLoader().load()
        converter = SheetDataConverter(rawFixtures)
    }

    @Test
    @Throws(Exception::class)
    fun testConvert() {
        val sheetData = converter!!.convert()

        assertThat(sheetData).isNotNull()
        val fixture1 = sheetData.fixtures.get(0)

        assertThat(fixture1.number).isEqualTo(1)
        assertThat(fixture1.date).isEqualTo("10/06/2016")
        assertThat(fixture1.hour).isEqualTo("21:00")
        assertThat(fixture1.phase).isEqualTo("Group_A")
        assertThat(fixture1.status).isEqualTo(Status.TO_PLAY.id)

        assertThat(fixture1.home.name).isEqualTo("France")
        assertThat(fixture1.home.winner).isFalse()
        assertThat(fixture1.home.isoAlpha2Code).isEqualTo("fr")
        assertThat(fixture1.home.goals).isEqualTo(0)
        assertThat(fixture1.away.name).isEqualTo("Romania")
        assertThat(fixture1.away.winner).isFalse()
        assertThat(fixture1.away.isoAlpha2Code).isEqualTo("ro")
        assertThat(fixture1.away.goals).isEqualTo(0)

        val group1 = sheetData.groups.get(0)
        assertThat(group1.code).isEqualTo("Group_A")
        assertThat(group1.members).hasSize(4)
        assertThat(group1.members).extracting("country").contains("France", "Romania", "Albania", "Switzerland")
        assertThat(group1.members).extracting("isoAlpha2Code").contains("fr", "ro", "al", "ch")

        assertThat(sheetData.countries).hasSize(24)
        val country1 = sheetData.countries.get(0)
        assertThat(country1.name).isEqualTo("France")
        assertThat(country1.isoAlpha2Code).isEqualTo("fr")
    }

}