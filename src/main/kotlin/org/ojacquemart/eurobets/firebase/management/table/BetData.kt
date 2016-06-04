package org.ojacquemart.eurobets.firebase.management.table

import org.ojacquemart.eurobets.firebase.management.match.Match
import org.ojacquemart.eurobets.firebase.management.user.User

data class BetData(val match: Match? = null,
                   val user: User? = null,
                   val bet: Bet? = null) {

    fun getResultPoints(): ResultPoints? {
        val result = getResult()
        if (result == null) return null
        else return ResultPoints(result = result, points = getPoints(result))
    }

    fun getResult(): Result? {
        if (bet == null) return null

        val perfectBet = isPerfectBet()
        if (perfectBet) return Result.PERFECT

        val match = match!!
        val matchScoreType = match.getScoreType()
        val betScoreType = bet.getScoreType()
        if (matchScoreType == betScoreType) return Result.GOOD

        return Result.BAD
    }

    private fun isPerfectBet(): Boolean {
        val matchHomeGoals = match?.home?.goals
        val matchAwayGoals = match?.away?.goals

        val betHomeGoals = bet?.homeGoals
        val betAwayGoals = bet?.awayGoals

        return matchHomeGoals == betHomeGoals && matchAwayGoals == betAwayGoals
    }

    private fun getPoints(result: Result): Int {
        val phase = match?.phase?.state
        val scale = PhaseScale.scale[phase]

        return scale?.pointsByResult?.get(result)!!
    }

}