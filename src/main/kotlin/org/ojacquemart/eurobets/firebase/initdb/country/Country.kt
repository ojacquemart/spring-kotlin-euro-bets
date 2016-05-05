package org.ojacquemart.eurobets.firebase.initdb.country

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonUnwrapped
import org.ojacquemart.eurobets.firebase.initdb.i18n.I18n

@JsonIgnoreProperties(ignoreUnknown = true)
data class Country(
        @JsonUnwrapped val i18n: I18n,
        @JsonProperty("alpha-2") val isoAlpha2Code: String) {
}