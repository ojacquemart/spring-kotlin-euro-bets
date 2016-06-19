package org.ojacquemart.eurobets.firebase.management.match.stat

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.Test
import org.ojacquemart.eurobets.firebase.management.match.Match
import org.ojacquemart.eurobets.firebase.management.match.Team
import org.ojacquemart.eurobets.firebase.management.table.Bet
import org.ojacquemart.eurobets.firebase.management.table.BetData
import org.ojacquemart.eurobets.firebase.management.table.DatasourceForTest

class StatMapperTest {

    val match1 = Match(number = 1,
            phase = DatasourceForTest.phaseGroup,
            home = Team(goals = 1, i18n = Team.I18Nested("foo", "foo")),
            away = Team(goals = 0, i18n = Team.I18Nested("bar", "bar")))

    // 10 bets for 1 - 0
    // 4 perfect, 1 good, 5 bad
    // 1-0: 4 (2 feeling lucky)
    // 1-1: 2
    // 1-2: 2
    // 2-0: 1
    // 0-0: 1
    val bets = listOf(
            BetData(match = match1, bet = null),
            BetData(match = match1, bet = Bet(homeGoals = 1, awayGoals = 0, timestamp = 1234, feelingLucky = true)),
            BetData(match = match1, bet = Bet(homeGoals = 1, awayGoals = 0, timestamp = 12345, feelingLucky = true)),
            BetData(match = match1, bet = Bet(homeGoals = 1, awayGoals = 0, timestamp = 123456)),
            BetData(match = match1, bet = Bet(homeGoals = 1, awayGoals = 0, timestamp = 12345678)),
            BetData(match = match1, bet = Bet(homeGoals = 1, awayGoals = 1, timestamp = 123456789)),
            BetData(match = match1, bet = Bet(homeGoals = 1, awayGoals = 1)),
            BetData(match = match1, bet = Bet(homeGoals = 1, awayGoals = 2)),
            BetData(match = match1, bet = Bet(homeGoals = 1, awayGoals = 2)),
            BetData(match = match1, bet = Bet(homeGoals = 2, awayGoals = 0)),
            BetData(match = match1, bet = Bet(homeGoals = 0, awayGoals = 0)),
            BetData(match = match1, bet = null)
    )

    @Test
    fun testMap() {
        val mapper = StatMapper()
        val stat = mapper.map(bets)

        assertThat(stat.matchNumber).isEqualTo(match1.number)

        assertThat(stat.nbBets).isEqualTo(10)
        assertThat(stat.lastBetTimestamp).isEqualTo(123456789)
        assertThat(stat.goodFeelingLuckyPercentage).isEqualTo(0.2F)
        assertThat(stat.scores.size).isEqualTo(5)

        assertThat(stat.scores[0]).isEqualTo(Stat.Score(homeGoals = 1, awayGoals = 0, percentage = 0.4F))
        assertThat(stat.scores[1]).isEqualTo(Stat.Score(homeGoals = 1, awayGoals = 1, percentage = 0.2F))
        assertThat(stat.scores[2]).isEqualTo(Stat.Score(homeGoals = 1, awayGoals = 2, percentage = 0.2F))
        assertThat(stat.scores[3]).isEqualTo(Stat.Score(homeGoals = 0, awayGoals = 0, percentage = 0.1F))
        assertThat(stat.scores[4]).isEqualTo(Stat.Score(homeGoals = 2, awayGoals = 0, percentage = 0.1F))

        assertThat(stat.betsResult.perfect).isEqualTo(0.4F)
        assertThat(stat.betsResult.good).isEqualTo(0.1F)
        assertThat(stat.betsResult.bad).isEqualTo(0.5F)

        assertThat(stat.winner.home).isEqualTo(0.5F)
        assertThat(stat.winner.draw).isEqualTo(0.3F)
        assertThat(stat.winner.away).isEqualTo(0.2F)
    }

}