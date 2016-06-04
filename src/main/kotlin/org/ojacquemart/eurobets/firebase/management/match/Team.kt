package org.ojacquemart.eurobets.firebase.management.match

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Team(val goals: Int? = -1,
                val winner: Boolean? = false)
