package org.ojacquemart.eurobets.firebase.initdb.i18n

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonIgnore

@JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.ANY)
data class I18n(val fr: String, val en: String) {
    @JsonIgnore
    val map = mapOf<String, String>("fr" to fr, "en" to en)
}
