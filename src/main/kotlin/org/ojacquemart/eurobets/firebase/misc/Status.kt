package org.ojacquemart.eurobets.firebase.misc

enum class Status constructor(val id: Int) {
    TO_PLAY(0),
    PLAYED(1),
    PLAYING(2);
}