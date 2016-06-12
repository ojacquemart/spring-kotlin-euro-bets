package org.ojacquemart.eurobets.firebase.management.match

data class MatchResultData(val number: Int,
                           val status: Int,
                           val homeGoals: Int,
                           val awayGoals: Int,
                           val homeWinner: Boolean,
                           val awayWinner: Boolean)