package org.ojacquemart.eurobets.firebase.initdb.country

import com.fasterxml.jackson.annotation.JsonProperty
import org.ojacquemart.eurobets.firebase.initdb.i18n.I18n
import org.ojacquemart.eurobets.util.JsonFileLoader

class CountriesJsonFileLoader {

    val FILE_NAME = "countries-iso-3166-2.json";

    val countryMapper: (RawCountry) -> Country = { rawCountry ->
        Country(I18n(rawCountry.fr, rawCountry.en), rawCountry.isoAlpha2Code)
    }

    class RawCountries(val countries: List<RawCountry>)
    class RawCountry(val fr: String, val en: String, @JsonProperty("alpha-2") val isoAlpha2Code: String)

    fun load(): Countries {
        val rawCountries = JsonFileLoader<RawCountries>().load(FILE_NAME, RawCountries::class.java)
        val countries = rawCountries.countries.map(countryMapper)

        return Countries(countries)
    }

}