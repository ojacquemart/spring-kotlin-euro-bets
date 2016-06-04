package org.ojacquemart.eurobets.firebase.management.user

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class User(val uid: String = "",
                val displayName: String = "",
                val profileImageURL: String = "",
                val leagues: Map<String, Boolean> = mapOf()
)