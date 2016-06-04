package org.ojacquemart.eurobets.firebase.initdb.fixture

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Fixture(
        val number: Int,
        val dayId: Int,
        val date: String,
        val dateTimestamp: Long,
        val hour: String,
        val timestamp: Long,
        val stadium: Stadium,
        val phase: Phase18n,
        val home: TeamI18n,
        val away: TeamI18n,
        val status: Int)