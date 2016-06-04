package org.ojacquemart.eurobets.firebase.misc

enum class PhaseType constructor(val state: String) {
    GROUP("group"),
    ROUND_16("Round16"),
    QUARTER("Quarter"),
    SEMI("SemiFinal"),
    FINAL("Final")
}
