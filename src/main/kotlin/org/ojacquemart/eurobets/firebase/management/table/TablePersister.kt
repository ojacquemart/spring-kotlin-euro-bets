package org.ojacquemart.eurobets.firebase.management.table

import org.ojacquemart.eurobets.firebase.Collections
import org.ojacquemart.eurobets.firebase.config.FirebaseRef
import org.ojacquemart.eurobets.firebase.management.league.League
import org.ojacquemart.eurobets.firebase.management.match.stat.StatPersister
import org.ojacquemart.eurobets.firebase.rx.RxFirebase
import org.ojacquemart.eurobets.lang.loggerFor
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch
import rx.lang.kotlin.onError

@Component
class TablePersister(val betsFetcher: BetsFetcher, val statPersister: StatPersister, val ref: FirebaseRef) {

    private val log = loggerFor<TablePersister>()

    var persistRunning = false

    fun persist() {
        when (persistRunning) {
            true -> log.info("Persist is already running...")
            false -> doPersist()
        }
    }

    private fun doPersist() {
        log.info("Compute & persist tables data")

        persistRunning = true

        val stopWatch = StopWatch()
        val onSubscribe = {
            log.info("Start to build & persist tables")
            stopWatch.start()
        }
        val onCompleted = {
            stopWatch.stop()
            log.info("Compute bets in ${stopWatch.totalTimeMillis}ms")
        }

        val obsBets = betsFetcher.getBets()
                .doOnSubscribe(onSubscribe)
                .doOnCompleted(onCompleted)
        obsBets.onError { error ->
            log.error("Error while persisting table", error)
            persistRunning = false
        }.subscribe { bets ->
            log.debug("Bets fetched... compute tables")

            persistGlobalTable(bets)
            persistLeaguesTables(bets)
            persistStat(bets)
            persistRunning = false

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

        table.table
                .filter { it.uid.isNotEmpty() }
                .forEachIndexed { index, it ->
                    val userPositionPoints = UserIndexed(index = index, position = it.position, points = it.points)
                    log.trace("Persist $userPositionPoints")

                    usersTableRef.child(it.uid).setValue(userPositionPoints)
                }
    }

    private fun persistStat(bets: List<BetData>) {
        statPersister.persist(bets)
    }

    private fun persistLeaguesTables(bets: List<BetData>) {
        RxFirebase.observeList(this.ref.firebase.child(Collections.leagues), League::class.java)
                .subscribe { leagues ->
                    leagues.forEach { league -> persistLeagueTable(bets, league) }
                }
    }

    private fun persistLeagueTable(bets: List<BetData>, league: League) {
        log.trace("Compute league ${league.slug} table")

        val leagueMemberUids = league.members.map { it.key }
        val leagueBets = bets.filter { leagueMemberUids.contains(it.user!!.uid) }
        val leagueTable = TableAssembler(leagueBets).getTable()

        ref.firebase.child(Collections.tablesLeagues).child(league.slug).setValue(leagueTable)
    }

    data class UserIndexed(val index: Int = -1, val position: Int = -1, val points: Int = -1)

}