package org.ojacquemart.eurobets.firebase.management.match

import org.ojacquemart.eurobets.firebase.management.match.crawler.Fixture
import org.ojacquemart.eurobets.firebase.misc.Status

data class MatchWithFixture(val match: Match, val fixture: Fixture) {

    fun getFinalMatch(): MatchResultData? {
        if (fixture.result == null) {
            return null
        }

        val result = fixture.result
        val awayGoals = result.goalsAwayTeam ?: 0
        val homeGoals = result.goalsHomeTeam ?: 0
        val homeWinner = homeGoals > awayGoals
        val awayWinner = homeGoals < awayGoals
        val status = if (fixture.status == org.ojacquemart.eurobets.firebase.management.match.crawler.Status.FINISHED) Status.PLAYED.id
        else Status.PLAYING.id

        return MatchResultData(
                number = match.number, status = status,
                homeGoals = result.goalsHomeTeam ?: 0, awayGoals = awayGoals,
                homeWinner = homeWinner, awayWinner = awayWinner)
    }
}