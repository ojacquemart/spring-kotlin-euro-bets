package org.ojacquemart.eurobets.firebase.support

enum class PhaseType constructor(val state: String) {
    GROUP("group"),
    ROUND_16("round16"),
    QUARTER("quarterfinal"),
    SEMI("semifinal"),
    FINAL("final")
}
