package org.ojacquemart.eurobets.firebase.misc

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Settings(val dayId: Int = 3,
                    val phase: String = "group",
                    val startedAt: Long = 1465585200000,
                    val started: Boolean = false) {

    fun isStarted(timestamp: Long) = if (started) true else timestamp >= startedAt
}