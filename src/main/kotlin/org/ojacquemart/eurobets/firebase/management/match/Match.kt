package org.ojacquemart.eurobets.firebase.management.match

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.ojacquemart.eurobets.firebase.misc.Status

@JsonIgnoreProperties(ignoreUnknown = true)
data class Match(val number: Int = -1, val timestamp: Long = -1, val status: Int = -1) {

    fun hasStarted() = this.status != Status.TO_PLAY.id
    fun isStarted(timestamp: Long) = if (hasStarted()) true else timestamp >= this.timestamp
}
