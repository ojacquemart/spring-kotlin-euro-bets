package org.ojacquemart.eurobets.firebase.initdb.country

import org.ojacquemart.eurobets.util.JsonFileLoader

class CountriesJsonFileLoader {

    val FILE_NAME = "iso-3166-2.json";

    fun load(): Iso3166Countries {
        return JsonFileLoader<Iso3166Countries>().load(FILE_NAME, Iso3166Countries::class.java)
    }

}