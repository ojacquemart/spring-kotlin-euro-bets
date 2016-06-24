package org.ojacquemart.eurobets.firebase.management.match

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.ojacquemart.eurobets.firebase.support.ScoreType
import org.ojacquemart.eurobets.firebase.support.Status
import java.util.concurrent.TimeUnit

@JsonIgnoreProperties(ignoreUnknown = true)
data class Match(val number: Int = -1,
                 val dayId: Int = -1,
                 val timestamp: Long = -1,
                 val phase: Phase? = null,
                 val status: Int = Status.TO_PLAY.id,
                 val home: Team? = null,
                 val away: Team? = null) {

    fun getRemainingTime(timestamp: Long): Long {
        return this.timestamp + TimeUnit.MINUTES.toMillis(MATCH_DURATION_IN_MINUTES) - timestamp
    }

    fun getRemainingTimeInMinutes(timestamp: Long): Long {
        return TimeUnit.MILLISECONDS.toMinutes(getRemainingTime(timestamp))
    }

    fun hasStarted() = this.status != Status.TO_PLAY.id
    fun isPlaying(timestamp: Long) = if (hasStarted()) true else {
        val isStarted = timestamp >= this.timestamp
        val remainingTime = getRemainingTime(timestamp)

        isStarted && remainingTime > 0L
    }

    fun getScoreGap(): Int {
        if (home == null || away == null) return 0
        return ScoreGap.toScoreGap(home.goals!!, away.goals!!)
    }

    fun getScoreType(): ScoreType {
        if (home == null || away == null) return ScoreType.UNDEFINED
        else return ScoreType.toScoreType(home.goals!!, away.goals!!)
    }

    companion object {
        val MATCH_DURATION_IN_MINUTES = 90L + 15L;
    }

}
