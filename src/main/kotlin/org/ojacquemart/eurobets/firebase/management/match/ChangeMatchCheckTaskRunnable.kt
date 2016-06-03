package org.ojacquemart.eurobets.firebase.management.match

import org.ojacquemart.eurobets.firebase.config.FirebaseRef
import org.ojacquemart.eurobets.lang.loggerFor

class ChangeMatchCheckTaskRunnable(val matches: List<Match>, val ref: FirebaseRef) : Runnable {

    private val log = loggerFor<ChangeMatchCheckTaskRunnable>()

    val matchStatusUpdater = MatchStatusUpdater()

    override fun run() {
        matches.forEach { match ->
            if (match.isStarted(System.currentTimeMillis())) {
                log.debug("Match ${match.number} is started!")
                matchStatusUpdater.update(match, ref)
            }
         }
    }

}