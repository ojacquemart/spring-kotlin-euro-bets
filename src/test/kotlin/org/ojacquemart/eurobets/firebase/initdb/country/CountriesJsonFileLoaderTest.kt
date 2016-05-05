package org.ojacquemart.eurobets.firebase.initdb.country

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.Test

class CountriesJsonFileLoaderTest {

    @Test
    fun testLoad() {
        val iso3166Countries = CountriesJsonFileLoader().load()

        assertThat(iso3166Countries).isNotNull()

        // make sure that we find england which is a particular case
        assertThat(iso3166Countries.countries.find{ country -> country.i18n.en.equals("England") }!!).isNotNull()
    }
}