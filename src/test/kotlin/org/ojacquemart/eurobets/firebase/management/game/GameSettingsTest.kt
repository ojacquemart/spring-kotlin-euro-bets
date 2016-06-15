package org.ojacquemart.eurobets.firebase.management.game

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.Test
import org.ojacquemart.eurobets.firebase.support.Settings

class GameSettingsTest {

    @Test
    fun testIsStarted() {
        assertThat(Settings(startedAt = 1465509600000, started = true).isStarted(1465509000000)).isTrue()
        assertThat(Settings(startedAt = 1465509600000, started = false).isStarted(1465509000000)).isFalse()
        assertThat(Settings(startedAt = 1465509600000, started = false).isStarted(1465509600000)).isTrue()
        assertThat(Settings(startedAt = 1465509600000, started = false).isStarted(1465509600001)).isTrue()
    }
}