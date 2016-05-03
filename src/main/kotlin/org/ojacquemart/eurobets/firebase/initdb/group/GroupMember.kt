package org.ojacquemart.eurobets.firebase.initdb.group

data class GroupMember(
        val country: String,
        val isoCode: String,
        val points: String,
        val win: String,
        val lose: String,
        val draw: String,
        val goalsFor: String,
        val goalsAgainst: String)