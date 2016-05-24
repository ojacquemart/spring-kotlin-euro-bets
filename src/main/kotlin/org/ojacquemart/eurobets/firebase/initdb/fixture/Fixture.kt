package org.ojacquemart.eurobets.firebase.initdb.fixture

data class Fixture(
        val number: Int,
        val dayId: Int,
        val date: String,
        val dateTimestamp: Long,
        val hour: String,
        val timestamp: Long,
        val stadium: Stadium,
        val phase: Phase,
        val home: Team,
        val away: Team,
        val status: Int)