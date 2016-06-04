package org.ojacquemart.eurobets.firebase.management.table

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.Test
import org.ojacquemart.eurobets.firebase.management.table.DatasourceForTest.Companion.awayWinner_0_1
import org.ojacquemart.eurobets.firebase.management.table.DatasourceForTest.Companion.draw_1_1
import org.ojacquemart.eurobets.firebase.management.table.DatasourceForTest.Companion.homeWinner_1_0
import org.ojacquemart.eurobets.firebase.management.table.DatasourceForTest.Companion.phaseFinal
import org.ojacquemart.eurobets.firebase.management.table.DatasourceForTest.Companion.phaseQuarter
import org.ojacquemart.eurobets.firebase.management.table.DatasourceForTest.Companion.phaseRound16
import org.ojacquemart.eurobets.firebase.management.table.DatasourceForTest.Companion.phaseSemi

class BetDataTest {

    val bet_1_0 = Bet(homeGoals = 1, awayGoals = 0)
    val bet_2_0 = Bet(homeGoals = 2, awayGoals = 0)
    val bet_0_1 = Bet(homeGoals = 0, awayGoals = 1)
    val bet_0_2 = Bet(homeGoals = 0, awayGoals = 2)
    val bet_0_0 = Bet(homeGoals = 0, awayGoals = 0)
    val bet_1_1 = Bet(homeGoals = 1, awayGoals = 1)

    @Test
    fun testGetPoints() {
        // home winner
        verifyBetPoints(BetData(match = homeWinner_1_0, bet = bet_1_0), expected = ResultPoints(Result.PERFECT, 10))
        verifyBetPoints(BetData(match = homeWinner_1_0.copy(phase = phaseRound16), bet = bet_1_0), expected = ResultPoints.perfect(25))
        verifyBetPoints(BetData(match = homeWinner_1_0.copy(phase = phaseQuarter), bet = bet_1_0), expected = ResultPoints.perfect(50))
        verifyBetPoints(BetData(match = homeWinner_1_0.copy(phase = phaseSemi), bet = bet_1_0), expected = ResultPoints.perfect(100))
        verifyBetPoints(BetData(match = homeWinner_1_0.copy(phase = phaseFinal), bet = bet_1_0), expected = ResultPoints.perfect(200))
        verifyBetPoints(BetData(match = homeWinner_1_0, bet = bet_2_0), expected = ResultPoints.good(3))
        verifyBetPoints(BetData(match = homeWinner_1_0.copy(phase = phaseRound16), bet = bet_2_0), expected = ResultPoints.good(10))
        verifyBetPoints(BetData(match = homeWinner_1_0.copy(phase = phaseQuarter), bet = bet_2_0), expected = ResultPoints.good(25))
        verifyBetPoints(BetData(match = homeWinner_1_0.copy(phase = phaseSemi), bet = bet_2_0), expected = ResultPoints.good(50))
        verifyBetPoints(BetData(match = homeWinner_1_0.copy(phase = phaseFinal), bet = bet_2_0), expected = ResultPoints.good(100))
        verifyBetPoints(BetData(match = homeWinner_1_0, bet = bet_0_0), expected = ResultPoints.bad())
        verifyBetPoints(BetData(match = homeWinner_1_0, bet = bet_0_1), expected = ResultPoints.bad())
        verifyBetPoints(BetData(match = homeWinner_1_0.copy(phase = phaseFinal), bet = bet_0_2), expected = ResultPoints.bad(1))

        // away winner
        verifyBetPoints(BetData(match = awayWinner_0_1, bet = bet_0_1), expected = ResultPoints.perfect(10))
        verifyBetPoints(BetData(match = awayWinner_0_1, bet = bet_0_2), expected = ResultPoints.good(3))
        verifyBetPoints(BetData(match = awayWinner_0_1, bet = bet_0_0), expected = ResultPoints.bad())
        verifyBetPoints(BetData(match = awayWinner_0_1, bet = bet_1_1), expected = ResultPoints.bad())

        // draw
        verifyBetPoints(BetData(match = draw_1_1, bet = bet_1_1), expected = ResultPoints.perfect(10))
        verifyBetPoints(BetData(match = draw_1_1, bet = bet_0_0), expected = ResultPoints.good(3))
        verifyBetPoints(BetData(match = draw_1_1, bet = bet_1_0), expected = ResultPoints.bad())
        verifyBetPoints(BetData(match = draw_1_1, bet = bet_0_1), expected = ResultPoints.bad())
    }

    fun verifyBetPoints(betData: BetData, expected: ResultPoints) {
        assertThat(betData.getResultPoints()).isEqualTo(expected)
    }
}