package org.ojacquemart.eurobets.firebase.initdb

import org.ojacquemart.eurobets.firebase.initdb.country.CountryFinder
import org.ojacquemart.eurobets.firebase.initdb.fixture.Fixture
import org.ojacquemart.eurobets.firebase.initdb.fixture.Stadium
import org.ojacquemart.eurobets.firebase.initdb.fixture.Status
import org.ojacquemart.eurobets.firebase.initdb.fixture.Team
import org.ojacquemart.eurobets.firebase.initdb.group.Group
import org.ojacquemart.eurobets.firebase.initdb.group.GroupMember
import org.ojacquemart.eurobets.firebase.initdb.raw.RawFixture
import org.ojacquemart.eurobets.firebase.initdb.raw.RawFixtures
import org.ojacquemart.eurobets.firebase.initdb.raw.RawGroupMember
import org.ojacquemart.eurobets.firebase.initdb.raw.Sheets
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class SheetDataConverter(val rawFixtures: RawFixtures) {

    val countryFinder = CountryFinder()
    val dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

    // TODO: complete when more info...
    val STATUS_BY_TEXT = mapOf(
            "fixture" to Status.TO_PLAY
    )

    fun convert(): SheetData {
        val sheets = this.rawFixtures.sheets

        val fixtures = getFixtures(sheets)
        val groups = getGroups(sheets)

        return SheetData(fixtures, groups)
    }

    private fun getFixtures(sheets: Sheets): List<Fixture> {
        return sheets.fixtures.map { rawFixture ->
            val stadium = getStadiumFromRawCityName(rawFixture.stadiumCity)
            val status = getStatusFromRawStatus(rawFixture.resultFixture)

            Fixture(rawFixture.id,
                    rawFixture.date,
                    rawFixture.kickoffTimeLocal,
                    getTimestamp(rawFixture),
                    stadium,
                    rawFixture.phase,
                    Team(rawFixture.homeTeam,
                            getCountryAlpha2Code(rawFixture.homeTeam),
                            safeToIntFromGoals(rawFixture.homeGoals)),
                    Team(rawFixture.awayTeam,
                            getCountryAlpha2Code(rawFixture.awayTeam),
                            safeToIntFromGoals(rawFixture.awayGoals)),
                    status)
        }
    }

    private fun getGroups(sheets: Sheets): List<Group> {
        return listOf(
                getGroup("Group_A", sheets.groupA),
                getGroup("Group_B", sheets.groupB),
                getGroup("Group_C", sheets.groupC),
                getGroup("Group_D", sheets.groupD),
                getGroup("Group_E", sheets.groupE),
                getGroup("Group_F", sheets.groupF)
        )
    }

    fun getGroup(groupName: String, rawGroupMembers: List<RawGroupMember>): Group {
        return Group(groupName,
                members = rawGroupMembers.map { rawGroupMember ->
                    GroupMember(country = rawGroupMember.country,
                            isoAlpha2Code = getCountryAlpha2Code(rawGroupMember.country),
                            points = rawGroupMember.points,
                            win = rawGroupMember.win, lose = rawGroupMember.lose, draw = rawGroupMember.draw,
                            goalsFor = rawGroupMember.goalsFor, goalsAgainst = rawGroupMember.goalsAgainst)
                })
    }

    fun getTimestamp(rawFixture: RawFixture): Long {
        val dateAsString = rawFixture.date + " " + rawFixture.kickoffTimeLocal

        return LocalDateTime.parse(dateAsString, dtf).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    fun getStadiumFromRawCityName(city: String): Stadium {
        val rawStadium = this.rawFixtures.sheets.stadia.find { stadium -> stadium.city == city }!!

        return Stadium(rawStadium.id, rawStadium.name, rawStadium.city)
    }

    fun getStatusFromRawStatus(status: String): Int {
        return STATUS_BY_TEXT.get(status)?.id!!
    }

    fun getCountryAlpha2Code(countryName: String): String {
        return countryFinder.find(countryName)
    }

    fun safeToIntFromGoals(goals: String): Int {
        return when (goals) {
            "" -> 0
            else -> goals.toInt()
        }
    }

}