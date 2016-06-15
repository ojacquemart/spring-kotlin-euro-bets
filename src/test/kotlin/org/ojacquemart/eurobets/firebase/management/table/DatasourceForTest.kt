package org.ojacquemart.eurobets.firebase.management.table

import org.ojacquemart.eurobets.firebase.management.match.Match
import org.ojacquemart.eurobets.firebase.management.match.Phase
import org.ojacquemart.eurobets.firebase.management.match.Team
import org.ojacquemart.eurobets.firebase.management.user.User
import org.ojacquemart.eurobets.firebase.support.PhaseType

class DatasourceForTest {
    companion object {
        val phaseGroup = Phase(state = PhaseType.GROUP.state)
        val phaseRound16 = Phase(state = PhaseType.ROUND_16.state)
        val phaseQuarter = Phase(state = PhaseType.QUARTER.state)
        val phaseSemi = Phase(state = PhaseType.SEMI.state)
        val phaseFinal = Phase(state = PhaseType.FINAL.state)

        val homeWinner_1_0 = Match(number = 1, phase = phaseGroup, home = Team(goals = 1), away = Team(goals = 0))
        val awayWinner_0_1 = Match(number = 2, phase = phaseGroup, home = Team(goals = 0), away = Team(goals = 1))
        val draw_1_1 = Match(number = 3, phase = phaseGroup, home = Team(goals = 1), away = Team(goals = 1))

        val match1_1_0 = homeWinner_1_0
        val match2_0_1 = awayWinner_0_1
        val match3_1_1 = draw_1_1
        val match4_2_4 = homeWinner_1_0.copy(number = 4, home = Team(goals = 2), away = Team(goals = 4))
        val match5_2_2 = homeWinner_1_0.copy(number = 5, home = Team(goals = 2), away = Team(goals = 2))
        val match6_1_0 = homeWinner_1_0.copy(number = 6, phase = phaseRound16, home = Team(goals = 1), away = Team(goals = 0))
        val match7_2_0 = homeWinner_1_0.copy(number = 7, phase = phaseRound16, home = Team(goals = 2), away = Team(goals = 0))

        // baz: start game on round16, 2 round16 perfect matches = 50 pts
        val baz = User(uid = "baz", displayName = "Baz", profileImageURL = "baz.png")
        // foo: perfect bets on 5 first matches = 50pts
        val foo = User(uid = "foo", displayName = "Foo", profileImageURL = "foo.png")
        // bar: only good bets on 5 first matches = 15pts
        val bar = User(uid = "bar", displayName = "Bar", profileImageURL = "bar.png")
        // qix: always losing... = 0pt
        val qix = User(uid = "qix", displayName = "Qix", profileImageURL = "qix.png")

        // foo should be first because of its number of perfect bets

        val bets = listOf(
                // foo
                BetData(match = match1_1_0, user = foo, bet = Bet(1, 0)),
                BetData(match = match2_0_1, user = foo, bet = Bet(0, 1)),
                BetData(match = match3_1_1, user = foo, bet = Bet(1, 1)),
                BetData(match = match4_2_4, user = foo, bet = Bet(2, 4)),
                BetData(match = match5_2_2, user = foo, bet = Bet(2, 2)),
                BetData(match = match6_1_0, user = foo, bet = null),
                BetData(match = match7_2_0, user = foo, bet = null),

                // baz
                BetData(match = match1_1_0, user = baz, bet = null),
                BetData(match = match2_0_1, user = baz, bet = null),
                BetData(match = match3_1_1, user = baz, bet = null),
                BetData(match = match4_2_4, user = baz, bet = null),
                BetData(match = match5_2_2, user = baz, bet = null),
                BetData(match = match6_1_0, user = baz, bet = Bet(1, 0)),
                BetData(match = match7_2_0, user = baz, bet = Bet(2, 0)),

                // bar
                BetData(match = match1_1_0, user = bar, bet = Bet(2, 0)),
                BetData(match = match2_0_1, user = bar, bet = Bet(0, 2)),
                BetData(match = match3_1_1, user = bar, bet = Bet(2, 2)),
                BetData(match = match4_2_4, user = bar, bet = Bet(0, 1)),
                BetData(match = match5_2_2, user = bar, bet = Bet(0, 0)),
                BetData(match = match6_1_0, user = bar, bet = null),
                BetData(match = match7_2_0, user = bar, bet = null),

                // qix
                BetData(match = match1_1_0, user = qix, bet = Bet(0, 1)),
                BetData(match = match2_0_1, user = qix, bet = Bet(1, 0)),
                BetData(match = match3_1_1, user = qix, bet = Bet(1, 0)),
                BetData(match = match4_2_4, user = qix, bet = Bet(1, 0)),
                BetData(match = match5_2_2, user = qix, bet = Bet(1, 0)),
                BetData(match = match6_1_0, user = qix, bet = Bet(0, 1)),
                BetData(match = match7_2_0, user = qix, bet = Bet(0, 1))
        )


    }
}
