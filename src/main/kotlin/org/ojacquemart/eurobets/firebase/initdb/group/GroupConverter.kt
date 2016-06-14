package org.ojacquemart.eurobets.firebase.initdb.group

import org.ojacquemart.eurobets.firebase.initdb.country.CountryFinder
import org.ojacquemart.eurobets.firebase.initdb.i18n.I18ns
import org.ojacquemart.eurobets.firebase.initdb.raw.RawGroupMember
import org.ojacquemart.eurobets.firebase.initdb.raw.Sheets

class GroupConverter {

    val countryFinder = CountryFinder()

    fun getGroups(sheets: Sheets): List<Group> {
        return listOf(
                getGroup("GroupA", sheets.groupA),
                getGroup("GroupB", sheets.groupB),
                getGroup("GroupC", sheets.groupC),
                getGroup("GroupD", sheets.groupD),
                getGroup("GroupE", sheets.groupE),
                getGroup("GroupF", sheets.groupF)
        )
    }

    private fun getGroup(groupName: String, rawGroupMembers: List<RawGroupMember>): Group {
        return Group(
                code = groupName,
                i18n = I18ns.getGroup(groupName),
                members = rawGroupMembers.map { rawGroupMember -> getGroupMember(rawGroupMember) }
        )
    }

    private fun getGroupMember(rawGroupMember: RawGroupMember): GroupMember {
        val country = countryFinder.find(rawGroupMember.country)

        return GroupMember(
                i18n = country.i18n,
                isoAlpha2Code = country.isoAlpha2Code,
                position = rawGroupMember.position.toInt(),
                points = rawGroupMember.points.toInt(),
                win = rawGroupMember.win.toInt(), lose = rawGroupMember.lose.toInt(), draw = rawGroupMember.draw.toInt(),
                goalsFor = rawGroupMember.goalsFor.toInt(), goalsAgainst = rawGroupMember.goalsAgainst.toInt())
    }

}