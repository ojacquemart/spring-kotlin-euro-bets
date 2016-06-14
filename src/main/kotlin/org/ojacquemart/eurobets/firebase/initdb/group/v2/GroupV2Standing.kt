package org.ojacquemart.eurobets.firebase.initdb.group.v2

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class GroupV2Standing(@JsonProperty("Group") val group: String,
                           @JsonProperty("Country") val country: String,
                           @JsonProperty("P") val position: String,
                           @JsonProperty("Pts") val points: String,
                           @JsonProperty("W") val win: String,
                           @JsonProperty("D") val draw: String,
                           @JsonProperty("L") val lose: String,
                           @JsonProperty("F") val goalsFor: String,
                           @JsonProperty("A") val goalsAgainst: String
)