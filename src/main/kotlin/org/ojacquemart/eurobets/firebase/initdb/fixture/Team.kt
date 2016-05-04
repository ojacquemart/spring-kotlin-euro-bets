package org.ojacquemart.eurobets.firebase.initdb.fixture

data class Team(
        val name: String,
        val isoAlpha2Code: String,
        val goals: Int,
        val winner: Boolean = false)