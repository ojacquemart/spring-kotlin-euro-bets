package org.ojacquemart.eurobets.firebase.management.game

import org.ojacquemart.eurobets.firebase.Collections
import org.ojacquemart.eurobets.firebase.config.FirebaseRef
import org.ojacquemart.eurobets.firebase.config.SchedulingSettings
import org.ojacquemart.eurobets.firebase.support.Settings
import org.ojacquemart.eurobets.firebase.rx.RxFirebase
import org.ojacquemart.eurobets.lang.loggerFor
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.support.CronTrigger
import org.springframework.stereotype.Component
import rx.Observable
import java.util.concurrent.ScheduledFuture
import javax.annotation.PostConstruct

@Component
open class StartGameTask(val schedulingConfig: SchedulingSettings,
                         val ref: FirebaseRef,
                         val taskScheduler: TaskScheduler) {

    private val log = loggerFor<StartGameTask>()

    var scheduled: ScheduledFuture<*>? = null

    @PostConstruct
    fun checkStart() {
        val obsSettings: Observable<Settings> = RxFirebase.observe(ref.firebase.child(Collections.settings))
                .map { ds -> ds.getValue(Settings::class.java) }

        obsSettings.subscribe { settings ->
            handleStartedFlag(settings)
        }
    }

    private fun handleStartedFlag(settings: Settings) {
        log.info("Game started status: ${settings.started}")

        when (settings.started) {
            true -> if (scheduled != null) {
                log.debug("Game is started. Cancel the scheduled task.")
                scheduled!!.cancel(true);
            }
            false -> {
                log.info("Game is not started. Set up the scheduled with cron=${schedulingConfig.cronStarted}")
                val updater = SettingsUpdater(ref)
                scheduled = taskScheduler.schedule(StartGameCheckRunnable(settings, updater), CronTrigger(schedulingConfig.cronStarted))
            }
        }
    }
}
