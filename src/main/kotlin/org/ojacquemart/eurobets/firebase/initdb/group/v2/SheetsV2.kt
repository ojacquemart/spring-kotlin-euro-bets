package org.ojacquemart.eurobets.firebase.initdb.group.v2

import com.fasterxml.jackson.annotation.JsonProperty

data class SheetsV2(@JsonProperty("Group_standings") val groupV2Standings: List<GroupV2Standing>)