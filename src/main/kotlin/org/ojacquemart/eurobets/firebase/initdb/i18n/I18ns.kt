package org.ojacquemart.eurobets.firebase.initdb.i18n

import org.ojacquemart.eurobets.firebase.initdb.group.Groups

object I18ns {
    val undefined = I18n("???", "???")

    private val regexCleanEndOfFinalPhase = Regex("""_\d+$""")
    private val regexCleanUnderscoreOrDash = Regex("[-|_]")

    private val groups = Groups.letters.associateBy({ "Group_" + it }, { I18n("Groupe " + it, "Group " + it) })
    private val phases = groups
            .plus("Round16" to I18n("Huitième de finale", "Round 16"))
            .plus("QuarterFinal" to I18n("Quarts de finale", "Quarter final"))
            .plus("SemiFinal" to I18n("Demi-finale", "Semi final"))
            .plus("Final" to I18n("Finale", "Final"))

    fun getGroup(groupCode: String) = groups.getOrElse(groupCode, { undefined })

    fun getPhase(phase: String): I18n {
        val phaseCleaned = if (phase.startsWith("Group")) {
            phase
        } else {
            phase.replace(regexCleanEndOfFinalPhase, "").replace(regexCleanUnderscoreOrDash, "")
        }

        return phases.getOrElse(phaseCleaned, { undefined })
    }
}
