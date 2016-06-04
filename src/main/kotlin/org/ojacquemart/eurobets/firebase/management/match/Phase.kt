package org.ojacquemart.eurobets.firebase.management.match

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Phase(
        val state: String = "",
        val code: String = "")