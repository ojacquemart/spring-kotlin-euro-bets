package org.ojacquemart.eurobets.firebase.management.match

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Team(val i18n: I18Nested? = null,
                val isoAlpha2Code: String? = null,
                val goals: Int? = -1,
                val winner: Boolean? = false) {

    fun getI18EnName(): String {
        return i18n!!.en
    }

    data class I18Nested(val en: String = "", val fr: String = "") {}
}


