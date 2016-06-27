package org.ojacquemart.eurobets.firebase.management.match

import com.firebase.client.ChildEventListener
import com.firebase.client.DataSnapshot
import com.firebase.client.FirebaseError
import org.ojacquemart.eurobets.firebase.Collections
import org.ojacquemart.eurobets.firebase.config.FirebaseRef
import org.ojacquemart.eurobets.firebase.support.Status
import org.ojacquemart.eurobets.firebase.support.TimeInterval
import org.ojacquemart.eurobets.lang.loggerFor
import org.springframework.scheduling.TaskScheduler
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.annotation.PostConstruct

@Component
class OnPlayingMatchChangeListener(val ref: FirebaseRef, val scheduler: TaskScheduler) {

    private val log = loggerFor<OnPlayingMatchChangeListener>()

    val finder = MatchResultFinder()

    @PostConstruct
    fun listen() {
        log.info("Listening to playing matches...")

        ref.firebase.child(Collections.matches).addChildEventListener(object : ChildEventListener {
            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
                handleMatchDataSnapshot(p0)
            }

            override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
                handleMatchDataSnapshot(p0)
            }

            override fun onChildRemoved(p0: DataSnapshot?) {
            }

            override fun onCancelled(p0: FirebaseError?) {
            }
        })
    }

    private fun handleMatchDataSnapshot(p0: DataSnapshot?) {
        p0?.let { ds ->
            val match = ds.getValue(Match::class.java)
            handleIfPlayingMatch(match)
        }
    }

    private fun handleIfPlayingMatch(match: Match) {
        if (match.status == Status.PLAYING.id) {
            val remainingMinutes = match.getRemainingTimeInMinutes(System.currentTimeMillis())
            if (remainingMinutes > 0) {
                log.info("Schedule check task for #${match.number}")
                log.debug("$remainingMinutes minutes remaining in match ${match.number}")
                scheduleAtEndOfMatch(match, TimeInterval.minutes(remainingMinutes))
            } else {
                log.debug("Match ${match.number} is finished but status is still at playing... Will try to update it")
                tryToUpdate(match)
            }
        }
    }

    fun scheduleAtEndOfMatch(match: Match, timeInterval: TimeInterval) {
        val ldt = LocalDateTime.now().plus(timeInterval.value, timeInterval.unit)
        val instant = ldt.atZone(ZoneId.systemDefault()).toInstant()
        val triggerDate = Date.from(instant)
        log.info("Schedule task at $ldt")

        scheduler.schedule({
            val maybeResult = tryToUpdate(match)
            if (maybeResult == null) {
                log.info("No result found... reschedule task in $RETRY_INTERVAL")

                scheduleAtEndOfMatch(match, RETRY_INTERVAL)
            }
        }, triggerDate)
    }

    fun tryToUpdate(match: Match): MatchResultData? {
        val maybeResult = finder.find(match)?.getFinalMatch()
        if (maybeResult == null) return null
        else {
            log.info("Result found... $maybeResult")
            val updater = MatchResultUpdater(ref)
            updater.update(maybeResult)

            return maybeResult
        }
    }

    companion object {
        val RETRY_INTERVAL = TimeInterval.seconds(15)
    }

}