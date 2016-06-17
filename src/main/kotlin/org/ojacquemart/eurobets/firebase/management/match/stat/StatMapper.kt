package org.ojacquemart.eurobets.firebase.management.match.stat

import org.ojacquemart.eurobets.firebase.management.table.Bet
import org.ojacquemart.eurobets.firebase.management.table.BetData
import org.ojacquemart.eurobets.firebase.management.table.Result
import org.ojacquemart.eurobets.firebase.support.ScoreType

class StatMapper {

    fun map(betsData: List<BetData>): Stat {
        val match = betsData[0].match!!

        val nonNullBets = betsData.filter { it.bet != null }
        val bets = nonNullBets.map { it.bet!! }
        val nbBets = bets.size

        val goodFeelingLuckyPercentage = goodFeelingLuckyPercentage(nonNullBets, nbBets)
        val lastBetTimestamp = bets.map { it.timestamp }.max() ?: 0

        val betResult = betResult(nonNullBets, nbBets)
        val winnerRepartition = winnerRepartition(nonNullBets, nbBets)
        val scores = scores(bets, nbBets)

        return Stat(
                match = match,
                nbBets = nbBets,
                goodFeelingLuckyPercentage = goodFeelingLuckyPercentage,
                lastBetTimestamp = lastBetTimestamp,
                betsResult = betResult,
                winner = winnerRepartition,
                scores = scores
        )
    }

    fun scores(bets: List<Bet>, nbBets: Int): List<Stat.Score> {
        val scoresByHomeAndAWay = bets.map { it -> Stat.Score(homeGoals = it.homeGoals, awayGoals = it.awayGoals, percentage = 0F) }
                .groupBy { it }

        return scoresByHomeAndAWay.map { score ->
            val scorePercentage = percentage(score.value.size, nbBets)

            score.key.copy(percentage = scorePercentage)
        }.sortedBy { -it.percentage }
    }

    fun goodFeelingLuckyPercentage(nonNullBets: List<BetData>, nbBets: Int): Float {
        val perfectAndFeelingLuckBets = nonNullBets
                .map {
                    val result = it.getResult()

                    (result == Result.PERFECT || result == Result.GOOD) && it.bet!!.feelingLucky
                }
                .filter { it }
                .size

        return percentage(perfectAndFeelingLuckBets, nbBets)
    }

    private fun betResult(nonNullBets: List<BetData>, nbBets: Int): Stat.BetResultRepartition {
        val betsByResult = nonNullBets.map { it.getResult()!! }
                .groupBy { it }
                .map { it -> Pair(it.key, percentage(it.value.orEmpty().size, nbBets)) }
                .associateBy({ it.first }, { it.second })

        return Stat.BetResultRepartition(
                perfect = betsByResult[Result.PERFECT] ?: 0F,
                good = betsByResult[Result.GOOD] ?: 0F,
                bad = betsByResult[Result.BAD] ?: 0F)
    }

    private fun winnerRepartition(nonNullBets: List<BetData>, nbBets: Int): Stat.WinnerRepartition {
        val winnerByScoreType = nonNullBets.groupBy { it.bet!!.getScoreType() }
                .map { it -> Pair(it.key, percentage(it.value.orEmpty().size, nbBets)) }
                .associateBy({ it.first }, { it.second })

        return Stat.WinnerRepartition(
                home = winnerByScoreType[ScoreType.HOME_WINNER] ?: 0F,
                draw = winnerByScoreType[ScoreType.DRAW] ?: 0F,
                away = winnerByScoreType[ScoreType.AWAY_WINNER] ?: 0F)
    }

    companion object {
        fun percentage(value: Int, divider: Int): Float = formatPercentage(value.toFloat().div(divider).times(100))

        fun formatPercentage(value: Float): Float = "%.1f".format(value).toFloat()
    }

}