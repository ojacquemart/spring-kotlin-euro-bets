package org.ojacquemart.eurobets.firebase.initdb.group

import org.ojacquemart.eurobets.firebase.initdb.i18n.I18n

data class GroupMember(
        val i18n: I18n,
        val isoAlpha2Code: String,
        val position: Int,
        val points: Int,
        val win: Int,
        val lose: Int,
        val draw: Int,
        val goalsFor: Int,
        val goalsAgainst: Int)