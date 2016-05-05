package org.ojacquemart.eurobets.firebase.initdb.country

import org.ojacquemart.eurobets.firebase.initdb.i18n.I18n

class CountryFinder {

    private val countries = CountriesJsonFileLoader().load()
    private val countriesByEnglishCountryName = countries.countries.associateBy({ it.i18n.en }, { it })

    fun find(countryName: String): Country {
        return countriesByEnglishCountryName.getOrElse(countryName, { Country(I18n(countryName, countryName), "???") })
    }

}
