package org.ojacquemart.eurobets.firebase.management.game

import org.ojacquemart.eurobets.firebase.Collections
import org.ojacquemart.eurobets.firebase.config.FirebaseRef
import org.ojacquemart.eurobets.firebase.config.SchedulingSettings
import org.ojacquemart.eurobets.firebase.rx.RxFirebase
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

    var scheduled: ScheduledFuture<*>? = null

    @PostConstruct
    fun checkStart() {
        val obsGameSettings: Observable<GameSettings> = RxFirebase.observe(ref.firebase.child(Collections.settings))
                .map { ds -> ds.getValue(GameSettings::class.java) }

        obsGameSettings.subscribe { settings ->
            handleStartedFlag(settings)
        }
    }

    private fun handleStartedFlag(gameSettings: GameSettings) {
        println("Game is started: ${gameSettings.started}")

        when (gameSettings.started) {
            true -> if (scheduled != null) {
                println("Game is started. Cancel the scheduled task.")
                scheduled!!.cancel(true);
            }
            false -> {
                println("Game is not started. Set up the scheduled with cron=${schedulingConfig.cronStarted}")
                val updater = SettingsUpdater(ref)
                scheduled = taskScheduler.schedule(StartGameCheckRunnable(gameSettings, updater), CronTrigger(schedulingConfig.cronStarted))
            }
        }
    }
}
