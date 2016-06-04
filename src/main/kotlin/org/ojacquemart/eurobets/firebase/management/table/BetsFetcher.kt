package org.ojacquemart.eurobets.firebase.management.table

import com.firebase.client.DataSnapshot
import org.ojacquemart.eurobets.firebase.Collections
import org.ojacquemart.eurobets.firebase.config.FirebaseRef
import org.ojacquemart.eurobets.firebase.management.match.Match
import org.ojacquemart.eurobets.firebase.management.user.User
import org.ojacquemart.eurobets.firebase.rx.RxFirebase
import org.ojacquemart.eurobets.lang.loggerFor
import org.springframework.stereotype.Component
import rx.Observable

@Component
open class BetsFetcher(val ref: FirebaseRef) {

    private val log = loggerFor<BetsFetcher>()

    fun getBets(): Observable<List<BetData>> {
        log.debug("Getting bets...")

        val obsMatches = RxFirebase.observeList(ref.firebase.child(Collections.matches), Match::class.java, true)

        val obsUsers = RxFirebase.observeList(ref.firebase.child(Collections.users), User::class.java)
        val obsBetsByUid = RxFirebase.observe(ref.firebase.child(Collections.bets))
                .map { ds ->
                    ds.children!!.map { dsUser ->
                        val uid = dsUser.key
                        val mapBets = dsUser.children.filter { it.key.equals("matches") }
                                .flatMap { combine(it) }
                                .associateBy({ it.matchNumber }, { it.bet })

                        UidMapMatchBet(uid, mapBets)
                    }.associateBy({ it.uid }, { it.bets })
                }
        val obsUserWithBets = Observable.zip(obsUsers, obsBetsByUid) { users, betsByUid ->
            users.map { user ->
                val uid = user.uid
                val bets = betsByUid[uid]

                UserMapMatchBet(user, bets)
            }
        }

        log.debug("Associate matches with users & bets")

        return obsMatches
                .flatMapIterable { it -> it }
                .filter { it.hasStarted() }
                .flatMap { match ->
                    obsUserWithBets.map { userBets ->
                        log.debug("Computing matches: ${match.number}")

                        combineMatchWithUser(match, userBets!!)
                    }
                }
                .doOnTerminate { log.info("Finish to concat all the bets") }
                .toList()
                .map { it -> it.flatMap { it -> it } }
                .doOnNext { it -> log.info("${it.size} bets to compute") }
    }

    fun combine(ds: DataSnapshot): List<MatchNumberBet> {
        return ds.children.map { it -> MatchNumberBet(it.key.toInt(), it.getValue(Bet::class.java)) }
    }

    private fun combineMatchWithUser(match: Match, userBets: List<UserMapMatchBet>): List<BetData> {
        return userBets.map { it ->
            val matchNumber = match.number
            val bet = it.bets?.get(matchNumber)

            BetData(match = match, user = it.user, bet = bet)
        }
    }

    data class UserMapMatchBet(val user: User, val bets: Map<Int, Bet>?)
    data class UidMapMatchBet(val uid: String, val bets: Map<Int, Bet>?)
    data class MatchNumberBet(val matchNumber: Int, val bet: Bet)

}