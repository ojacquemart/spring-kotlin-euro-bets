package org.ojacquemart.eurobets.firebase.management.match

import org.ojacquemart.eurobets.firebase.Collections
import org.ojacquemart.eurobets.firebase.config.FirebaseRef
import org.ojacquemart.eurobets.firebase.config.SchedulingSettings
import org.ojacquemart.eurobets.firebase.misc.Status
import org.ojacquemart.eurobets.firebase.rx.RxFirebase
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.support.CronTrigger
import org.springframework.stereotype.Component
import rx.Observable
import java.util.concurrent.ScheduledFuture
import javax.annotation.PostConstruct

@Component
open class ChangeMatchStatusTask(val schedulingConfig: SchedulingSettings,
                                 val ref: FirebaseRef,
                                 val taskScheduler: TaskScheduler) {

    var scheduled: ScheduledFuture<*>? = null

    @PostConstruct
    fun changeStatuses() {
        val obsMatches: Observable<List<Match>> = RxFirebase.observe(ref.firebase.child(Collections.matches))
                .map { ds -> ds!!.children }
                .map { ds ->
                    ds.map { data -> data.getValue(Match::class.java) }
                            .filter { match -> match.status == Status.TO_PLAY.id }
                }

        obsMatches.subscribe { matches ->
            if (!matches.isEmpty()) {
                handleMatches(matches)
            } else {
                println("All matches have been started!")
            }
        }
    }

    private fun handleMatches(matches: List<Match>) {
        println("Schedule task to update ${matches.size} matches")
        if (scheduled != null) scheduled!!.cancel(true)

        scheduled = taskScheduler.schedule(ChangeMatchCheckTaskRunnable(matches, ref), CronTrigger(schedulingConfig.cronMatches))
    }

}