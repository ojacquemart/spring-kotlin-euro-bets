package org.ojacquemart.eurobets.firebase.management.table

import org.ojacquemart.eurobets.firebase.management.match.Match
import org.ojacquemart.eurobets.firebase.management.match.Phase
import org.ojacquemart.eurobets.firebase.management.user.User
import org.ojacquemart.eurobets.firebase.support.ScoreType

data class BetData(val match: Match? = null,
                   val user: User? = null,
                   val bet: Bet? = null,
                   val winner: Winner? = null) {

    fun getResultPoints(): ResultPoints? {
        val result = getResult()
        if (result == null) return null
        else return ResultPoints(result = result, points = getPoints(result))
    }

    fun getFinalePoints(): Int {
        return winner?.good.let { goodFinale ->
            when (goodFinale) {
                true -> PhaseScale.finaleWinner
                else -> 0
            }
        }
    }

    fun getResult(): Result? {
        if (bet == null) return null

        val perfectBet = isPerfectBet()
        if (perfectBet) return Result.PERFECT

        val match = match!!
        val matchScoreType = match.getScoreType()
        val betScoreType = bet.getScoreType()
        if (matchScoreType == betScoreType) return getGoodGapOrGoodResult(matchScoreType)

        return Result.BAD
    }

    private fun getGoodGapOrGoodResult(matchScoreType: ScoreType): Result? {
        if (matchScoreType == ScoreType.DRAW) return Result.GOOD

        val match = match!!
        val matchScoreGap = match.getScoreGap()
        val betScoreGap = bet!!.getScoreGap()
        if (!match.phase!!.state.equals(Phase.groupState) && matchScoreGap == betScoreGap) return Result.GOOD_GAP

        return Result.GOOD
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
