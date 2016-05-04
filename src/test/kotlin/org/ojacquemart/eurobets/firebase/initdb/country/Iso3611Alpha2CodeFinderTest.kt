package org.ojacquemart.eurobets.firebase.initdb.country

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat

import org.junit.Test

class Iso3611Alpha2CodeFinderTest {

    @Test
    fun testFind() {
        val finder = CountryFinder()

        assertThat(finder.find("England")).isEqualTo("gb-eng")
        assertThat(finder.find("Scotland")).isEqualTo("gb-sct")
        assertThat(finder.find("France")).isEqualTo("fr")
        assertThat(finder.find("???")).isEmpty()
    }

}