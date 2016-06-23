package org.ojacquemart.eurobets.firebase.management.table

import org.ojacquemart.eurobets.firebase.Collections
import org.ojacquemart.eurobets.firebase.config.FirebaseRef
import org.ojacquemart.eurobets.firebase.management.match.stat.StatPersister
import org.ojacquemart.eurobets.lang.loggerFor
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch
import rx.lang.kotlin.onError

@Component
class TablePersister(val betsFetcher: BetsFetcher, val leagueTableMapper: LeagueTableMapper,
                     val statPersister: StatPersister, val ref: FirebaseRef) {

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
        log.debug("Persist bets stats data")

        statPersister.persist(bets)
    }

    private fun persistLeaguesTables(bets: List<BetData>) {
        log.debug("Persist leagues tables")

        val nbMatches = bets.map { it.match!! }.map { it.number }.max()!!

        leagueTableMapper.map(bets)
                .subscribe { leaguesTables ->
                    persistGlobalLeaguesTable(nbMatches, leaguesTables)
                    persistLeaguesTable(leaguesTables)
                }
    }

    private fun persistGlobalLeaguesTable(currentMatchNumber: Int, leagueTables: List<LeagueTable>) {
        log.debug("Compute & persist global leagues table")
        val rows = LeaguesTableCalculator().getRows(currentMatchNumber, leagueTables)

        ref.firebase.child(Collections.tablesGlobalLeagues).setValue(rows)
    }

    private fun persistLeaguesTable(leaguesTable: List<LeagueTable>) {
        log.debug("Compute & persist single leagues table")

        leaguesTable.forEach { leagueTable ->
            val league = leagueTable.league
            val table = leagueTable.table

            ref.firebase.child(Collections.tablesLeagues).child(league.slug).setValue(table)
        }
    }

    data class UserIndexed(val index: Int = -1, val position: Int = -1, val points: Int = -1)

}