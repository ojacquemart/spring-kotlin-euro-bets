package org.ojacquemart.eurobets.firebase.initdb.group

data class Group(
        val code: String,
        val members: List<GroupMember>)