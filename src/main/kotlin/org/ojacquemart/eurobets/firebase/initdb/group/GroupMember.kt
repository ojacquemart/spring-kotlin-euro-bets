package org.ojacquemart.eurobets.firebase.initdb.group

import org.ojacquemart.eurobets.firebase.initdb.i18n.I18n

data class GroupMember(
        val i18n: I18n,
        val isoAlpha2Code: String,
        val points: String,
        val win: String,
        val lose: String,
        val draw: String,
        val goalsFor: String,
        val goalsAgainst: String)