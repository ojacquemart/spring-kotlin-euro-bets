package org.ojacquemart.eurobets.firebase.support

import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit

data class TimeInterval(val value: Long, val unit: TemporalUnit) {
    companion object {
        fun minutes(value: Long) = TimeInterval(value, ChronoUnit.MINUTES)
        fun seconds(value: Long) = TimeInterval(value, ChronoUnit.SECONDS)
    }
}