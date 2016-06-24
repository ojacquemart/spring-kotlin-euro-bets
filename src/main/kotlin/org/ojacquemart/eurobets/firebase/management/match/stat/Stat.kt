package org.ojacquemart.eurobets.firebase.management.match.stat

data class Stat(val matchNumber: Int,
                val nbBets: Int,
                val lastBetTimestamp: Long,
                val goodFeelingLuckyPercentage: Float,
                val winner: WinnerRepartition,
                val betsResult: BetResultRepartition,
                val scores: List<Score>, override val timestamp: Long): NewsObject {

    override fun getType(): NewsType {
        return NewsType.STAT
    }

    data class WinnerRepartition(val home: Float, val draw: Float, val away: Float)
    data class BetResultRepartition(val perfect: Float, val goodGap: Float, val good: Float, val bad: Float)
    data class Score(val homeGoals: Int, val awayGoals: Int, val percentage: Float)

}
