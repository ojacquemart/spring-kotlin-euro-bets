package org.ojacquemart.eurobets.firebase.management.game

class StartGameCheckRunnable(val gameSettings: GameSettings, val updater: SettingsUpdater): Runnable {

    override fun run() {
        println("Check if game is started...")

        if (gameSettings.isStarted(System.currentTimeMillis())) {
            println("Game started...")
            updater.start()
        } else {
            println("Game not started yet. Nothing to do!")
        }
    }

}