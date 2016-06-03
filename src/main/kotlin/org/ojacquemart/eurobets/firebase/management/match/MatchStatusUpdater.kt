package org.ojacquemart.eurobets.firebase.management.match

import org.ojacquemart.eurobets.firebase.Collections
import org.ojacquemart.eurobets.firebase.config.FirebaseRef
import org.ojacquemart.eurobets.firebase.misc.Status
import org.ojacquemart.eurobets.lang.loggerFor

class MatchStatusUpdater {

    private val log = loggerFor<MatchStatusUpdater>()

    fun update(match: Match, ref: FirebaseRef) {
        log.info("Update match ${match.number}")

        ref.firebase.child(Collections.matches).child(match.number.toString())
                .updateChildren(mapOf("status" to Status.PLAYED.id)) { error, ref ->
                    when (error) {
                        null -> log.debug("Match ${match.number} is set to ${Status.PLAYED}")
                        else -> log.error("Error while updating the match ${match.number}")
                    }
                }
    }

}