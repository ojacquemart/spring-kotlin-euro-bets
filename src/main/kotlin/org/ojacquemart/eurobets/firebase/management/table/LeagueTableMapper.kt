package org.ojacquemart.eurobets.firebase.management.table

import org.ojacquemart.eurobets.firebase.Collections
import org.ojacquemart.eurobets.firebase.config.FirebaseRef
import org.ojacquemart.eurobets.firebase.management.league.League
import org.ojacquemart.eurobets.firebase.rx.RxFirebase
import org.ojacquemart.eurobets.lang.loggerFor
import org.springframework.stereotype.Component
import rx.Observable

@Component
class LeagueTableMapper(val ref: FirebaseRef) {

    private val log = loggerFor<LeagueTableMapper>()

    fun map(bets: List<BetData>): Observable<List<LeagueTable>> {
        log.debug("Map league with table...")

        return RxFirebase.observeList(this.ref.firebase.child(Collections.leagues), League::class.java, true)
                .flatMapIterable { it -> it }
                .map { it -> mapToLeagueTable(it, bets) }
                .toList()
    }

    private fun mapToLeagueTable(league: League, bets: List<BetData>): LeagueTable {
        log.trace("Compute ${league.slug} table")
        val leagueMemberUids = league.members.map { it.key }
        val leagueBets = bets.filter { leagueMemberUids.contains(it.user!!.uid) }
        val table = TableAssembler(leagueBets).getTable()

        return LeagueTable(league, table)
    }

}