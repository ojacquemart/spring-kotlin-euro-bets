package org.ojacquemart.eurobets.firebase.initdb

import org.ojacquemart.eurobets.firebase.initdb.country.Country
import org.ojacquemart.eurobets.firebase.initdb.country.CountryFinder
import org.ojacquemart.eurobets.firebase.initdb.fixture.*
import org.ojacquemart.eurobets.firebase.initdb.group.Group
import org.ojacquemart.eurobets.firebase.initdb.group.GroupConverter
import org.ojacquemart.eurobets.firebase.initdb.i18n.I18ns
import org.ojacquemart.eurobets.firebase.initdb.raw.RawFixture
import org.ojacquemart.eurobets.firebase.initdb.raw.RawFixtures
import org.ojacquemart.eurobets.firebase.initdb.raw.Sheets
import org.ojacquemart.eurobets.firebase.support.Settings
import org.ojacquemart.eurobets.firebase.support.Status
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class SheetDataConverter(val rawFixtures: RawFixtures) {

    val countryFinder = CountryFinder()
    val groupConverter = GroupConverter()

    val ddMMyyyyHHmm = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
    val ddMMyyyy = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    // TODO: complete when more info...
    val STATUS_BY_TEXT = mapOf(
            "fixture" to Status.TO_PLAY
    )

    fun convert(): SheetData {
        val sheets = this.rawFixtures.sheets

        val fixtures = getFixtures(sheets)
        val groups = getGroups(sheets)
        val countries = getCountries(groups)

        return SheetData(Settings(), fixtures, groups, countries)
    }

    // fixtures

    private fun getFixtures(sheets: Sheets): Map<String, Fixture> {
        return sheets.fixtures.map { rawFixture ->
            val stadium = getStadiumFromRawCityName(rawFixture.stadiumCity)
            val status = getStatusFromRawStatus(rawFixture.resultFixture)

            Fixture(rawFixture.id,
                    getSafeDayId(rawFixture),
                    rawFixture.date,
                    getTimestampDate(rawFixture),
                    rawFixture.kickoffTimeLocal,
                    getTimestamp(rawFixture),
                    stadium,
                    getPhase(rawFixture.phase),
                    home = getTeam(rawFixture.homeTeam, rawFixture.homeGoals),
                    away = getTeam(rawFixture.awayTeam, rawFixture.awayGoals),
                    status = status)
        }.associateBy({ it.number.toString() }, { it })
    }

    private fun getTimestampDate(rawFixture: RawFixture): Long {
        val dt = LocalDate.parse(rawFixture.date, ddMMyyyy).atStartOfDay(ZoneId.systemDefault())

        return dt.toInstant().toEpochMilli()
    }

    fun getTimestamp(rawFixture: RawFixture): Long {
        val dateAsString = rawFixture.date + " " + rawFixture.kickoffTimeLocal
        val ldt = LocalDateTime.parse(dateAsString, ddMMyyyyHHmm).atZone(ZoneId.systemDefault()).toLocalDateTime()

        return ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    private fun getSafeDayId(rawFixture: RawFixture) = if (rawFixture.dayId != null) rawFixture.dayId.toInt() else -1

    // teams

    private fun getTeam(teamName: String, teamGoals: String): TeamI18n {
        val country = getCountryByName(teamName)

        return TeamI18n(country.i18n, country.isoAlpha2Code, safeToIntFromGoals(teamGoals))
    }

    private fun getPhase(phase: String): Phase18n {
        val phaseCode = Phases.clean(phase)
        val phaseState = if (phase.startsWith("Group")) "group" else phaseCode.toLowerCase()

        return Phase18n(state = phaseState, code = phaseCode, i18n = I18ns.getPhase(phase))
    }

    fun getStatusFromRawStatus(status: String): Int {
        return STATUS_BY_TEXT.get(status)?.id!!
    }

    fun getStadiumFromRawCityName(city: String): Stadium {
        val rawStadium = this.rawFixtures.sheets.stadia.find { stadium -> stadium.city == city }!!

        return Stadium(rawStadium.id, rawStadium.name, rawStadium.city.replace(Regex("""\s\(.*\)$"""), ""))
    }

    // groups

    private fun getGroups(sheets: Sheets): List<Group> {
        return groupConverter.getGroups(sheets)
    }

    // helper methods

    private fun getCountries(groups: List<Group>): List<Country> {
        return groups
                .flatMap { group -> group.members }
                .map { groupMember -> Country(groupMember.i18n, groupMember.isoAlpha2Code) }
    }

    fun getCountryByName(countryName: String): Country {
        return countryFinder.find(countryName)
    }

    fun safeToIntFromGoals(goals: String): Int {
        return when (goals) {
            "" -> 0
            else -> goals.toInt()
        }
    }
}
