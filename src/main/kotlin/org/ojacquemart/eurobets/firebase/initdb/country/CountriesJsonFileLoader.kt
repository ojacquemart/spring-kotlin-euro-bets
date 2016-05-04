package org.ojacquemart.eurobets.firebase.initdb.country

import org.ojacquemart.eurobets.util.JsonFileLoader

class CountriesJsonFileLoader {

    val FILE_NAME = "countries-iso-3166-2.json";

    val isoAlpha2CodeToLowerCaseMapper: (Country) -> Country = { country ->
        Country(country.name, country.isoAlpha2Code.toLowerCase())
    }

    fun load(): Countries {
        val iso3166Countries = JsonFileLoader<Countries>().load(FILE_NAME, Countries::class.java)

        return Countries(iso3166Countries.countries.map(isoAlpha2CodeToLowerCaseMapper))
    }

}