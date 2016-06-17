package org.ojacquemart.eurobets.firebase.management.table

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.ojacquemart.eurobets.firebase.support.ScoreType

@JsonIgnoreProperties(ignoreUnknown = true)
data class Bet(val homeGoals: Int = -1,
               val awayGoals: Int = -1,
               val feelingLucky: Boolean = false,
               val timestamp: Long = -1) {
    fun getScoreType() = ScoreType.toScoreType(homeGoals, awayGoals)
}

