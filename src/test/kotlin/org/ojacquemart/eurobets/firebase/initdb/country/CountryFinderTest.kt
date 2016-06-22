package org.ojacquemart.eurobets.firebase.initdb.country

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat

import org.junit.Test
import org.ojacquemart.eurobets.firebase.initdb.i18n.I18n

class CountryFinderTest {

    @Test
    fun testFind() {
        val finder = CountryFinder()

        assertThat(finder.find("England")).isEqualTo(Country(I18n("Angleterre", "England"), "gb-eng"))
        assertThat(finder.find("Scotland")).isEqualTo(Country(I18n("Ecosse", "Scotland"), "gb-sct"))
        assertThat(finder.find("France")).isEqualTo(Country(I18n("France", "France"), "fr"))
        assertThat(finder.find("France ")).isEqualTo(Country(I18n("France", "France"), "fr"))
        assertThat(finder.find("???")).isEqualTo(Country(I18n("???", "???"), "???"))
    }

}