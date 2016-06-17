package org.ojacquemart.eurobets.firebase.management.match.stat

import org.ojacquemart.eurobets.firebase.management.match.Match

data class Stat(val match: Match,
                val nbBets: Int,
                val lastBetTimestamp: Long,
                val goodFeelingLuckyPercentage: Float,
                val winner: WinnerRepartition,
                val betsResult: BetResultRepartition,
                val scores: List<Score>) {

    data class WinnerRepartition(val home: Float, val draw: Float, val away: Float)
    data class BetResultRepartition(val perfect: Float, val good: Float, val bad: Float)
    data class Score(val homeGoals: Int, val awayGoals: Int, val percentage: Float)

}
