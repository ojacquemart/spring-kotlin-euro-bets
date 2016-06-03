package org.ojacquemart.eurobets.firebase.management.game

import org.ojacquemart.eurobets.lang.loggerFor

class StartGameCheckRunnable(val gameSettings: GameSettings, val updater: SettingsUpdater): Runnable {

    private val log = loggerFor<StartGameCheckRunnable>()

    override fun run() {
        log.info("Check if game is started...")

        if (gameSettings.isStarted(System.currentTimeMillis())) {
            log.info("Game started...")
            updater.start()
        } else {
            log.debug("Game not started yet. Nothing to do!")
        }
    }

}