package org.ojacquemart.eurobets.firebase.management.table

import org.ojacquemart.eurobets.firebase.misc.PhaseType

data class PhaseScale(val pointsByResult: Map<Result, Int>) {

    companion object {

        val scale = mapOf(
                PhaseType.GROUP.state        to PhaseScale(mapOf(Result.PERFECT to 10, Result.GOOD to 3, Result.BAD to 0)),
                PhaseType.ROUND_16.state     to PhaseScale(mapOf(Result.PERFECT to 25, Result.GOOD to 10, Result.BAD to 0)),
                PhaseType.QUARTER.state      to PhaseScale(mapOf(Result.PERFECT to 50, Result.GOOD to 25, Result.BAD to 0)),
                PhaseType.SEMI.state         to PhaseScale(mapOf(Result.PERFECT to 100, Result.GOOD to 50, Result.BAD to 0)),
                PhaseType.FINAL.state        to PhaseScale(mapOf(Result.PERFECT to 200, Result.GOOD to 100, Result.BAD to 1))
        )

    }
}