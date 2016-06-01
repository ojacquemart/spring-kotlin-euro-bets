package org.ojacquemart.eurobets.firebase.management.game

import org.ojacquemart.eurobets.firebase.Collections
import org.ojacquemart.eurobets.firebase.config.FirebaseRef

class SettingsUpdater(val ref: FirebaseRef) {

    fun start() {
        ref.firebase.child(Collections.settings).updateChildren(mapOf("started" to true)) { error, ref ->
            when (error) {
                null -> println("Settings 'started' has been changed..")
                else -> println("Error while updating the settings...")
            }
        }
    }

}