package org.ojacquemart.eurobets.firebase.initdb.raw

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

data class RawFixtures(val sheets: Sheets) {
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class Sheets(@JsonProperty("NewFixtures") val fixtures: List<RawFixture>,
                  val stadia: List<Stadia>,
                  @JsonProperty("Group_A") val groupA: List<RawGroupMember>,
                  @JsonProperty("Group_B") val groupB: List<RawGroupMember>,
                  @JsonProperty("Group_C") val groupC: List<RawGroupMember>,
                  @JsonProperty("Group_D") val groupD: List<RawGroupMember>,
                  @JsonProperty("Group_E") val groupE: List<RawGroupMember>,
                  @JsonProperty("Group_F") val groupF: List<RawGroupMember>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class RawFixture(
        @JsonProperty("Match No") val id: Int,
        val date: String,
        @JsonProperty("Match_Categ") val phase: String,
        @JsonProperty("Home_team") val homeTeam: String,
        @JsonProperty("Home_Goals") val homeGoals: String,
        @JsonProperty("Away_team") val awayTeam: String,
        @JsonProperty("Away_Goals") val awayGoals: String,
        @JsonProperty("Resultfixture") val resultFixture: String,
        @JsonProperty("kickofftimelocal") val kickoffTimeLocal: String,
        @JsonProperty("City") val stadiumCity: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Stadia(
        @JsonProperty("number") val id: Int,
        val city: String,
        val name: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class RawGroupMember(@JsonProperty("Country") val country: String,
                          @JsonProperty("P") val position: String,
                          @JsonProperty("Pts") val points: String,
                          @JsonProperty("W") val win: String,
                          @JsonProperty("D") val draw: String,
                          @JsonProperty("L") val lose: String,
                          @JsonProperty("F") val goalsFor: String,
                          @JsonProperty("A") val goalsAgainst: String
)