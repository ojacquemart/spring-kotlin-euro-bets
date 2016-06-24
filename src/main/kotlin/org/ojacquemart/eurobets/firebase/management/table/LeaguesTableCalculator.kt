package org.ojacquemart.eurobets.firebase.management.table

import org.ojacquemart.eurobets.lang.loggerFor
import kotlin.comparisons.compareBy
import kotlin.comparisons.thenBy

class LeaguesTableCalculator {

    private val log = loggerFor<LeaguesTableCalculator>()

    fun getRows(nbMatches: Int, leaguesTables: List<LeagueTable>): List<LeagueStatRow> {
        log.info("Compute global leagues tables at match #$nbMatches")

        val toLeagueStatRow: (LeagueTable) -> LeagueStatRow = { leagueTable ->
            val league = leagueTable.league
            val table = leagueTable.table
            val players = table.nbRows
            val tableReduced = reduce(table)

            val pointsAvg = tableReduced.points.toFloat().div(players)
            val leagueBets = tableReduced.bets

            LeagueStatRow(name = league.name, slug = league.slug,
                    players = table.nbRows,
                    points = pointsAvg,
                    assiduity = leagueBets.toFloat().div(nbMatches * players),
                    perfects = tableReduced.perfects.toFloat().div(leagueBets),
                    goodGaps = tableReduced.goodGaps.toFloat().div(leagueBets),
                    goods = tableReduced.goods.toFloat().div(leagueBets),
                    bads = tableReduced.bads.toFloat().div(leagueBets))
        }

        val leaguesStatRows = leaguesTables
                .filter(atLeastOneMember)
                .map(toLeagueStatRow)

        val positions = getPositions(leaguesStatRows)

        return leaguesStatRows
                .map { leagueStatRow ->
                    val position = positions.indexOf(leagueStatRow.points) + 1

                    leagueStatRow.copy(position = position)
                }.sortedWith(comparator)
    }

    fun reduce(leagueTable: Table): TableRow {
        return leagueTable.table.reduce { current, next ->
            current.copy(bets = current.bets + next.bets,
                    points = current.points + next.points,
                    perfects = current.perfects + next.perfects,
                    goodGaps = current.goodGaps + next.goodGaps,
                    goods = current.goods + next.goods,
                    bads = current.bads + next.bads)
        }
    }

    private fun getPositions(leaguesStatRows: List<LeagueStatRow>): List<Float> {
        val positions = leaguesStatRows
                .groupBy { it.points }
                .toList()
                .map { pair -> pair.first }
                .sortedDescending()
        return positions
    }

    companion object {

        val atLeastOneMember: (LeagueTable) -> Boolean = { it.table.nbRows > 1 }

        val comparator = compareBy<LeagueStatRow> { it.position }
                .thenBy { -it.perfects }.thenBy { -it.goodGaps }.thenBy { -it.goods }
                .thenBy { -it.assiduity }
    }

}