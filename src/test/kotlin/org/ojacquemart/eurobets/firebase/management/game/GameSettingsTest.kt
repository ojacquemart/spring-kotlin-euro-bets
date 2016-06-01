package org.ojacquemart.eurobets.firebase.management.game

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.Test

class GameSettingsTest {

    @Test
    fun testIsStarted() {
        assertThat(GameSettings(1465509600000, true).isStarted(1465509000000)).isTrue()
        assertThat(GameSettings(1465509600000, false).isStarted(1465509000000)).isFalse()
        assertThat(GameSettings(1465509600000, false).isStarted(1465509600000)).isTrue()
        assertThat(GameSettings(1465509600000, false).isStarted(1465509600001)).isTrue()
    }
}