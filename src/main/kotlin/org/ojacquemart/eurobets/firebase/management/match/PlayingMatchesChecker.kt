package org.ojacquemart.eurobets.firebase.management.match

import org.ojacquemart.eurobets.firebase.config.FirebaseRef
import org.ojacquemart.eurobets.lang.loggerFor

class PlayingMatchesChecker(val matches: List<Match>, val ref: FirebaseRef) : Runnable {

    private val log = loggerFor<PlayingMatchesChecker>()

    val updater = PlayingMatchesUpdater()

    override fun run() {
        val playingMatches = matches.filter { match ->
            val nowInMillis = System.currentTimeMillis()

            match.isPlaying(nowInMillis)
        }

        playingMatches.forEach { match ->
            updater.update(match, ref)
            log.debug("Match ${match.number} is playing!")
        }
    }

}