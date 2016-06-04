package org.ojacquemart.eurobets.firebase.management.match

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.ojacquemart.eurobets.firebase.misc.ScoreType
import org.ojacquemart.eurobets.firebase.misc.Status

@JsonIgnoreProperties(ignoreUnknown = true)
data class Match(val number: Int = -1,
                 val dayId: Int = -1,
                 val timestamp: Long = -1,
                 val phase: Phase? = null,
                 val status: Int = Status.TO_PLAY.id,
                 val home: Team? = null,
                 val away: Team? = null) {

    fun hasStarted() = this.status != Status.TO_PLAY.id
    fun isStarted(timestamp: Long) = if (hasStarted()) true else timestamp >= this.timestamp

    fun getScoreType(): ScoreType {
        if (home == null || away == null) return ScoreType.UNDEFINED
        else return ScoreType.toScoreType(home.goals!!, away.goals!!)
    }
}
