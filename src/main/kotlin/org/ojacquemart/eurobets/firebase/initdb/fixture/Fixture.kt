package org.ojacquemart.eurobets.firebase.initdb.fixture

import org.ojacquemart.eurobets.firebase.initdb.i18n.I18n

data class Fixture(
        val number: Int,
        val date: String,
        val dateTimestamp: Long,
        val hour: String,
        val timestamp: Long,
        val stadium: Stadium,
        val phase: Phase,
        val home: Team,
        val away: Team,
        val status: Int)