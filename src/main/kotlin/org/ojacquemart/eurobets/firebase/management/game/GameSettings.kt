package org.ojacquemart.eurobets.firebase.management.game

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class GameSettings(val startedAt: Long = -1, val started: Boolean = false) {

    fun isStarted(timestamp: Long) = if (started) true else timestamp >= startedAt
}