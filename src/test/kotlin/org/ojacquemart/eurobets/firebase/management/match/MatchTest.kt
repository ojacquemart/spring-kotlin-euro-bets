package org.ojacquemart.eurobets.firebase.management.match

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.Test
import org.ojacquemart.eurobets.firebase.support.ScoreType

class MatchTest {

    @Test
    fun testGetScoreType() {
        assertThat(Match().getScoreType()).isEqualTo(ScoreType.UNDEFINED)
        assertThat(Match(home = Team(goals = 2), away = Team(goals = 1)).getScoreType()).isEqualTo(ScoreType.HOME_WINNER)
        assertThat(Match(home = Team(goals = 1), away = Team(goals = 2)).getScoreType()).isEqualTo(ScoreType.AWAY_WINNER)
        assertThat(Match(home = Team(goals = 1), away = Team(goals = 1)).getScoreType()).isEqualTo(ScoreType.DRAW)
    }

}