package org.ojacquemart.eurobets.firebase.management.table

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.Test
import org.ojacquemart.eurobets.firebase.support.ScoreType

class BetTest {

    @Test
    fun testGetScoreType() {
        assertThat(Bet(homeGoals = 1, awayGoals = 0).getScoreType()).isEqualTo(ScoreType.HOME_WINNER)
        assertThat(Bet(homeGoals = 0, awayGoals = 1).getScoreType()).isEqualTo(ScoreType.AWAY_WINNER)
        assertThat(Bet(homeGoals = 2, awayGoals = 2).getScoreType()).isEqualTo(ScoreType.DRAW)
    }

}