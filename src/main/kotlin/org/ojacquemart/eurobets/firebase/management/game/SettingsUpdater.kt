package org.ojacquemart.eurobets.firebase.management.game

import org.ojacquemart.eurobets.firebase.Collections
import org.ojacquemart.eurobets.firebase.config.FirebaseRef
import org.ojacquemart.eurobets.lang.loggerFor

class SettingsUpdater(val ref: FirebaseRef) {

    private val logger = loggerFor<SettingsUpdater>()

    fun start() {
        ref.firebase.child(Collections.settings).updateChildren(mapOf("started" to true)) { error, ref ->
            when (error) {
                null -> logger.info("Settings 'started' has been changed..")
                else -> logger.info("Error while updating the settings...")
            }
        }
    }

}