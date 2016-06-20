package org.ojacquemart.eurobets.firebase.management.group

import com.firebase.client.DataSnapshot
import com.firebase.client.FirebaseError
import com.firebase.client.ValueEventListener
import org.ojacquemart.eurobets.firebase.Collections
import org.ojacquemart.eurobets.firebase.config.FirebaseRef
import org.ojacquemart.eurobets.lang.loggerFor
import org.springframework.scheduling.TaskScheduler
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Component
class GroupsUpdateScheduler(val taskScheduler: TaskScheduler, val groupsUpdater: GroupsUpdater, val ref: FirebaseRef) {

    private val log = loggerFor<GroupsUpdateScheduler>()

    var updateScheduled = false

    fun scheduleUpdate() {
        log.debug("Check if groups updated is needed")

        if (!updateScheduled) {
            updateScheduled = true

            ref.firebase.child(Collections.settings).child("dayId")
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: FirebaseError?) {
                            updateScheduled = false
                        }

                        override fun onDataChange(p0: DataSnapshot?) {
                            doUpdateIfDayInGroupPhase(p0?.getValue(Int::class.java))
                            updateScheduled = false
                        }
                    })
        }

    }

    private fun doUpdateIfDayInGroupPhase(dayId: Int?) {
        if (dayId != null && isDayInGroupPhase(dayId)) {
            log.debug("Current game day id: $dayId")

            doScheduleUpdate()
        } else {
            log.info("No groups update needed")
        }
    }

    private fun isDayInGroupPhase(dayId: Int) = dayId <= MAX_DAY_ID_GROUP_PHASE

    private fun doScheduleUpdate() {
        val updateDate = getUpdateDate()

        log.info("Schedule groups update in $UPDATE_DELAY_IN_MINUTES minutes et $updateDate")

        taskScheduler.schedule({
            groupsUpdater.update()
        }, updateDate)
    }

    private fun getUpdateDate(): Date {
        log.debug("Build update date")
        val ldtInDelay = LocalDateTime.now().plusMinutes(UPDATE_DELAY_IN_MINUTES)

        return Date.from(ldtInDelay.atZone(ZoneId.systemDefault()).toInstant())
    }

    companion object {
        val UPDATE_DELAY_IN_MINUTES = 15L
        val MAX_DAY_ID_GROUP_PHASE = 3
    }

}