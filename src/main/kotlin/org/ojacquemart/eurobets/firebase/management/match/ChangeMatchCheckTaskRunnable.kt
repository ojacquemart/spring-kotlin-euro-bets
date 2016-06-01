package org.ojacquemart.eurobets.firebase.management.match

import org.ojacquemart.eurobets.firebase.config.FirebaseRef

class ChangeMatchCheckTaskRunnable(val matches: List<Match>, val ref: FirebaseRef) : Runnable {

    val matchStatusUpdater = MatchStatusUpdater()

    override fun run() {
        matches.forEach { match ->
            if (match.isStarted(System.currentTimeMillis())) {
                println("Match ${match.number} is started!")
                matchStatusUpdater.update(match, ref)
            }
         }
    }

}