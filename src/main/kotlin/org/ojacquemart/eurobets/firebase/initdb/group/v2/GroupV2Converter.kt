package org.ojacquemart.eurobets.firebase.initdb.group.v2

import org.ojacquemart.eurobets.firebase.initdb.country.CountryFinder
import org.ojacquemart.eurobets.firebase.initdb.group.Group
import org.ojacquemart.eurobets.firebase.initdb.group.GroupMember
import org.ojacquemart.eurobets.firebase.initdb.i18n.I18ns

class GroupV2Converter {

    val countryFinder = CountryFinder()

    fun getGroups(rawFixtures: FixturesV2): List<Group> {
        val rawGroups = rawFixtures.sheets.groupV2Standings.filter { it.group.isNotEmpty() }.groupBy { it.group }

        return rawGroups.map { group ->
            getGroup("Group" + group.key, group.value)
        }
    }

    private fun getGroup(groupName: String, rawGroupMembers: List<GroupV2Standing>): Group {
        return Group(
                code = groupName,
                i18n = I18ns.getGroup(groupName),
                members = rawGroupMembers.map { rawGroupMember -> getGroupMember(rawGroupMember) }
        )
    }

    private fun getGroupMember(rawGroupMember: GroupV2Standing): GroupMember {
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