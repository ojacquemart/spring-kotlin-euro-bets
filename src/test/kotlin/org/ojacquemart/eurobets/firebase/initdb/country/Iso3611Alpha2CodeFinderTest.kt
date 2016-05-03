package org.ojacquemart.eurobets.firebase.initdb.country

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat

import org.junit.Test

class Iso3611Alpha2CodeFinderTest {

    @Test
    fun testFind() {
        val finder = Iso3611Alpha2CodeFinder()

        assertThat(finder.findIso3611Alpha2Code("England")).isEqualTo("gb-eng")
        assertThat(finder.findIso3611Alpha2Code("Scotland")).isEqualTo("gb-sct")
        assertThat(finder.findIso3611Alpha2Code("France")).isEqualTo("fr")
        assertThat(finder.findIso3611Alpha2Code("???")).isEmpty()
    }

}