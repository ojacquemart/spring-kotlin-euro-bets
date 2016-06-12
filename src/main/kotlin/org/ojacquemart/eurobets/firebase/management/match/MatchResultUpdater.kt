package org.ojacquemart.eurobets.firebase.management.match

import com.firebase.client.Firebase
import org.ojacquemart.eurobets.firebase.Collections
import org.ojacquemart.eurobets.firebase.config.FirebaseRef
import org.ojacquemart.eurobets.lang.loggerFor

class MatchResultUpdater(val ref: FirebaseRef) {

    private val log = loggerFor<MatchResultUpdater>()

    fun completionListener(message: String) = Firebase.CompletionListener { p0, p1 ->
        if (p0 == null) {
            log.debug("Success... $message")
        } else {
            log.error("Error... $message", p0!!.toException())
        }
    }

    fun update(maybeMatchResult: MatchResultData?) {
        maybeMatchResult?.let { matchResult ->
            log.info("Update result: $matchResult")

            ref.firebase.child(Collections.matches).child(matchResult.number.toString())
                    .updateChildren(mapOf("status" to matchResult.status), completionListener("Status update"))

            ref.firebase.child(Collections.matches).child(matchResult.number.toString())
                    .child("home")
                    .updateChildren(mapOf("goals" to matchResult.homeGoals, "winner" to matchResult.homeWinner),
                            completionListener("Home data update"))

            ref.firebase.child(Collections.matches).child(matchResult.number.toString())
                    .child("away")
                    .updateChildren(mapOf("goals" to matchResult.awayGoals, "winner" to matchResult.awayWinner),
                            completionListener("Away data update"))
        }
    }

}