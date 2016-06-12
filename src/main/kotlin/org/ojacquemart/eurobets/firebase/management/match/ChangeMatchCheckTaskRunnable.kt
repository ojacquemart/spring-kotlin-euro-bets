package org.ojacquemart.eurobets.firebase.management.match

import org.ojacquemart.eurobets.firebase.config.FirebaseRef
import org.ojacquemart.eurobets.lang.loggerFor
import java.util.concurrent.TimeUnit

class ChangeMatchCheckTaskRunnable(val matches: List<Match>, val ref: FirebaseRef) : Runnable {

    private val log = loggerFor<ChangeMatchCheckTaskRunnable>()

    val matchStatusUpdater = MatchStatusUpdater()

    override fun run() {
        matches.forEach { match ->
            val nowInMillis = System.currentTimeMillis()
            if (match.isStarted(nowInMillis)) {
                log.debug("Match ${match.number} is started!")

                matchStatusUpdater.update(match, ref)
            } else {
                val startsInMillis = match.timestamp - nowInMillis
                val startsInMinutes = TimeUnit.MILLISECONDS.toMinutes(startsInMillis)

                log.trace("Match ${match.number} starts in $startsInMinutes minutes")
            }
        }
    }

}