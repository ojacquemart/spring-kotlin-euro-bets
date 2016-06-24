package org.ojacquemart.eurobets.firebase.management.match

class ScoreGap {

    companion object {
        fun toScoreGap(homeGoals: Int, awayGoals: Int): Int {
            return Math.abs(homeGoals - awayGoals)
        }
    }

}