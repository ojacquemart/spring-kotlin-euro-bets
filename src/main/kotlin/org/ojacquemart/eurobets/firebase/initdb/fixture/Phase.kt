package org.ojacquemart.eurobets.firebase.initdb.fixture

import com.fasterxml.jackson.annotation.JsonUnwrapped
import org.ojacquemart.eurobets.firebase.initdb.i18n.I18n

data class Phase(
        val code: String,
        @JsonUnwrapped val i18n: I18n)