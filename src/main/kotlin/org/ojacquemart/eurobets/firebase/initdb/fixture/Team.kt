package org.ojacquemart.eurobets.firebase.initdb.fixture

import com.fasterxml.jackson.annotation.JsonUnwrapped
import org.ojacquemart.eurobets.firebase.initdb.i18n.I18n

data class Team(
        @JsonUnwrapped val i18n: I18n,
        val isoAlpha2Code: String,
        val goals: Int,
        val winner: Boolean = false)