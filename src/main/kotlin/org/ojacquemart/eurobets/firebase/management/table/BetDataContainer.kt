package org.ojacquemart.eurobets.firebase.management.table

data class BetDataContainer(val lastMatchNumber: Int, val bets: List<BetData>) {

    fun getBetsFromLastMatch(): List<BetData> = bets.filter { bet -> bet.match!!.number == lastMatchNumber }
}