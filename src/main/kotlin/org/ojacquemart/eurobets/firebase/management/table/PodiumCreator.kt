package org.ojacquemart.eurobets.firebase.management.table

import org.ojacquemart.eurobets.lang.loggerFor

class PodiumCreator(val tableRows: List<TableRow>) {

    private val log = loggerFor<PodiumCreator>()

    val isBetweenOneAndThree: (TableRow) -> Boolean = { it.position >= 1 && it.position <= 3 }
    val mapTableRowToPodiumMember: (TableRow) -> PodiumMember = {
        PodiumMember(it.uid, it.displayName, it.profileImageURL)
    }

    fun create(): Podium {
        log.debug("Create podium...")

        val tableRowsForPodium = tableRows.filter(isBetweenOneAndThree)
        val tableRowsByPosition = tableRowsForPodium.groupBy { it.position }

        val steps = tableRowsByPosition.toList()
                .map { PodiumStep(it.first, it.second.map(mapTableRowToPodiumMember)) }
                .associateBy({ it.number }, { it.members })

        return Podium(steps)
    }

}
