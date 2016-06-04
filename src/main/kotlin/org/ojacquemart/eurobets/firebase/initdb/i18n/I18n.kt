package org.ojacquemart.eurobets.firebase.initdb.i18n

import com.fasterxml.jackson.annotation.JsonIgnore

data class I18n(val fr: String, val en: String) {
    @JsonIgnore
    private val map = mapOf("fr" to fr, "en" to en)

    companion object {
        val undefined = I18n("???", "???")
    }
}
