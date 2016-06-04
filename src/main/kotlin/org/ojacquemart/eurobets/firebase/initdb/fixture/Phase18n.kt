package org.ojacquemart.eurobets.firebase.initdb.fixture

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonUnwrapped
import org.ojacquemart.eurobets.firebase.initdb.i18n.I18n

@JsonIgnoreProperties(ignoreUnknown = true)
data class Phase18n(
        val state: String = "",
        val code: String = "",
        @JsonUnwrapped val i18n: I18n = I18n.undefined)