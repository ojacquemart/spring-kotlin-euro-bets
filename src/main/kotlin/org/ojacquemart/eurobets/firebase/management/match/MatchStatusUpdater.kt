    package org.ojacquemart.eurobets.firebase.management.match

    import org.ojacquemart.eurobets.firebase.Collections
    import org.ojacquemart.eurobets.firebase.config.FirebaseRef
    import org.ojacquemart.eurobets.firebase.misc.Status

    class MatchStatusUpdater {

        fun update(match: Match, ref: FirebaseRef) {
            println("Update match ${match.number}")

            ref.firebase.child(Collections.matches).child(match.number.toString())
                    .updateChildren(mapOf("status" to Status.PLAYED.id)) { error, ref ->
                        when (error) {
                            null -> println("Match ${match.number} is set to ${Status.PLAYED}")
                            else -> println("Error while updating the match ${match.number}")
                        }
                    }
        }

    }