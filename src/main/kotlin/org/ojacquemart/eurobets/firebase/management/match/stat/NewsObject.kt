package org.ojacquemart.eurobets.firebase.management.match.stat

interface NewsObject {
    val timestamp: Long
    fun getType(): NewsType
}