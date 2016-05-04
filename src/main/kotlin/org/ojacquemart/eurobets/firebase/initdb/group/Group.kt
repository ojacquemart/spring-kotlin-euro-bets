package org.ojacquemart.eurobets.firebase.initdb.group

import com.fasterxml.jackson.annotation.JsonUnwrapped
import org.ojacquemart.eurobets.firebase.initdb.i18n.I18n

data class Group(
        val code: String,
        @JsonUnwrapped val label: I18n,
        val members: List<GroupMember>)
