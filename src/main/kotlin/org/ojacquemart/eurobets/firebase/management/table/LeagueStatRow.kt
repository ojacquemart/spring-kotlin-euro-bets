package org.ojacquemart.eurobets.firebase.management.table

data class LeagueStatRow(val slug: String,
                         val name: String,
                         val players: Int,
                         val position: Int = 0,
                         val points: Float,
                         val assiduity: Float,
                         val perfects: Float,
                         val goods: Float,
                         val bads: Float)