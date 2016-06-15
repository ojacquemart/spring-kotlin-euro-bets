package org.ojacquemart.eurobets.firebase.support

enum class ScoreType {
    UNDEFINED,
    HOME_WINNER,
    AWAY_WINNER,
    DRAW;

    companion object {
        fun toScoreType(homeGoals: Int, awayGoals: Int): ScoreType {
            if (homeGoals > awayGoals) return ScoreType.HOME_WINNER
            if (homeGoals < awayGoals) return ScoreType.AWAY_WINNER
            return ScoreType.DRAW
        }
    }

}
