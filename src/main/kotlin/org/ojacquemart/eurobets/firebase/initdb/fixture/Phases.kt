package org.ojacquemart.eurobets.firebase.initdb.fixture

object Phases {

    private val regexCleanEndOfFinalPhase = Regex("""_\d+$""")
    private val regexCleanUnderscoreOrDash = Regex("[-|_]")

    fun clean(phase: String): String {
        return phase.replace(regexCleanEndOfFinalPhase, "").replace(regexCleanUnderscoreOrDash, "")
    }

}
