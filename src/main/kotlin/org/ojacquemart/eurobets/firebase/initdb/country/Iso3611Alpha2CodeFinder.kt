package org.ojacquemart.eurobets.firebase.initdb.country

class Iso3611Alpha2CodeFinder {

    private val countries = CountriesJsonFileLoader().load()
    private val iso3611Alpha2CodesByCountryName = countries.countries.associateBy({ it.name }, { it.alpha2.toLowerCase() })

    fun findIso3611Alpha2Code(countryName: String): String {
        return iso3611Alpha2CodesByCountryName.get(countryName).orEmpty()
    }

}
