package org.ojacquemart.eurobets.firebase.initdb.fixture

data class Fixture(
        val number: Int,
        val date: String,
        val hour: String,
        val timestamp: Long,
        val stadium: Stadium,
        val phase: Phase,
        val home: Team,
        val away: Team,
        val status: Int)