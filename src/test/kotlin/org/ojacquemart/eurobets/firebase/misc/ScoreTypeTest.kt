package org.ojacquemart.eurobets.firebase.misc

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.Test

class ScoreTypeTest {

    @Test
    fun testToScoreType() {
        assertThat(ScoreType.toScoreType(0, 0)).isEqualTo(ScoreType.DRAW)
        assertThat(ScoreType.toScoreType(2, 2)).isEqualTo(ScoreType.DRAW)
        assertThat(ScoreType.toScoreType(1, 0)).isEqualTo(ScoreType.HOME_WINNER)
        assertThat(ScoreType.toScoreType(2, 1)).isEqualTo(ScoreType.HOME_WINNER)
        assertThat(ScoreType.toScoreType(0, 1)).isEqualTo(ScoreType.AWAY_WINNER)
        assertThat(ScoreType.toScoreType(1, 2)).isEqualTo(ScoreType.AWAY_WINNER)
    }
}