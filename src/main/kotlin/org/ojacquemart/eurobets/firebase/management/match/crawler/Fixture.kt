package org.ojacquemart.eurobets.firebase.management.match.crawler

import org.ojacquemart.eurobets.firebase.management.match.Match

data class Fixture(val status: Status,
                   val homeTeamName: String = "",
                   val awayTeamName: String = "",
                   val result: FixtureResult? = null) {

    fun isFinished(): Boolean = status == Status.FINISHED

    fun matches(match: Match): Boolean {
        return homeTeamName.equals(match.home!!.getI18EnName(), true) && awayTeamName.equals(match.away!!.getI18EnName(), true)
    }

}
