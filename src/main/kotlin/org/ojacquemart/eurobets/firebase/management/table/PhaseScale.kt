package org.ojacquemart.eurobets.firebase.management.table

import org.ojacquemart.eurobets.firebase.support.PhaseType

data class PhaseScale(val pointsByResult: Map<Result, Int>) {

    companion object {

        private val group =     PhaseScale(mapOf(Result.PERFECT to 10, Result.GOOD to 3, Result.BAD to 0))
        private val round16 =   PhaseScale(mapOf(Result.PERFECT to 25, Result.GOOD_GAP to 18, Result.GOOD to 10, Result.BAD to 0))
        private val quarter =   PhaseScale(mapOf(Result.PERFECT to 50, Result.GOOD_GAP to 35, Result.GOOD to 25, Result.BAD to 0))
        private val semi =      PhaseScale(mapOf(Result.PERFECT to 100, Result.GOOD_GAP to 80, Result.GOOD to 50, Result.BAD to 0))
        private val final =     PhaseScale(mapOf(Result.PERFECT to 200, Result.GOOD_GAP to 160, Result.GOOD to 100, Result.BAD to 1))

        val scale = mapOf(
                PhaseType.GROUP.state       to group,
                PhaseType.ROUND_16.state    to round16,
                PhaseType.QUARTER.state     to quarter,
                PhaseType.SEMI.state        to semi,
                PhaseType.FINAL.state       to final
        )

        val finaleWinner = 50

    }
}