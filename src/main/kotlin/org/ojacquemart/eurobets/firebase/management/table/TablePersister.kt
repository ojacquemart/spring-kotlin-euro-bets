package org.ojacquemart.eurobets.firebase.management.table

import org.ojacquemart.eurobets.firebase.Collections
import org.ojacquemart.eurobets.firebase.config.FirebaseRef
import org.ojacquemart.eurobets.firebase.management.league.League
import org.ojacquemart.eurobets.firebase.rx.RxFirebase
import org.ojacquemart.eurobets.lang.loggerFor
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class TablePersister(val betsFetcher: BetsFetcher, val ref: FirebaseRef) {

    private val log = loggerFor<TablePersister>()

    @PostConstruct
    fun persist() {
        log.info("Start to build & persist tables")

        val obsBets = betsFetcher.getBets()
        obsBets.subscribe { bets ->
            log.debug("Bets fetched... compute tables")

            persistGlobalTable(bets)
            persistLeaguesTables(bets)

            log.info("Finish to persist bets...")
        }
    }

    private fun persistGlobalTable(bets: List<BetData>) {
        log.debug("Persist global table")

        val table = TableAssembler(bets).getTable()
        ref.firebase.child(Collections.tables).setValue(table)

        persistUserPointsAndPosition(table)
    }

    private fun persistUserPointsAndPosition(table: Table) {
        log.debug("Persist user position & points")

        val usersTableRef = ref.firebase.child(Collections.usersTableMeta)
        table.table.forEach { it ->
            log.debug("Persist ${it.uid} position & points")
            usersTableRef.child(it.uid).setValue(UserPositionPoints(position = it.position, points = it.points))
         }
    }

    private fun persistLeaguesTables(bets: List<BetData>) {
        RxFirebase.observeList(this.ref.firebase.child(Collections.leagues), League::class.java)
                .subscribe { leagues ->
                    leagues.forEach { league -> persistLeagueTable(bets, league) }
                }
    }

    private fun persistLeagueTable(bets: List<BetData>, league: League) {
        log.debug("Compute league ${league.slug} table")

        val leagueMemberUids = league.members.map { it.key }
        val leagueBets = bets.filter { leagueMemberUids.contains(it.user!!.uid) }
        val leagueTable = TableAssembler(leagueBets).getTable()

        ref.firebase.child(Collections.tablesLeagues).child(league.slug).setValue(leagueTable)
    }

    data class UserPositionPoints(val position: Int = -1, val points: Int = -1)

}