package org.ojacquemart.eurobets.firebase.initdb.country

class CountryFinder {

    private val countries = CountriesJsonFileLoader().load()
    private val iso3611Alpha2CodesByCountryName = countries.countries.associateBy({ it.name }, { it.isoAlpha2Code })

    fun find(countryName: String): String {
        return iso3611Alpha2CodesByCountryName.get(countryName).orEmpty()
    }

}
