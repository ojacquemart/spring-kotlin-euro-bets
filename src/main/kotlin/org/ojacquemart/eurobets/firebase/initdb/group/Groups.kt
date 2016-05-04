package org.ojacquemart.eurobets.firebase.initdb.group

import org.ojacquemart.eurobets.firebase.initdb.i18n.I18n
import org.ojacquemart.eurobets.firebase.initdb.i18n.I18ns

object Groups {
    val letters = listOf("A", "B", "C", "D", "E", "F")
    private val map = letters.associateBy({ "Group_" + it }, { I18n("Groupe " + it, "Group " + it) })

    fun getI18n(groupCode: String) = map.getOrElse(groupCode, { I18ns.undefined })
}
