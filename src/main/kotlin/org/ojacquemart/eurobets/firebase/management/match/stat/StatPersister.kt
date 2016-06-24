package org.ojacquemart.eurobets.firebase.management.match.stat

import org.ojacquemart.eurobets.firebase.Collections
import org.ojacquemart.eurobets.firebase.config.FirebaseRef
import org.ojacquemart.eurobets.firebase.management.table.BetData
import org.ojacquemart.eurobets.lang.loggerFor
import org.springframework.stereotype.Component

@Component
class StatPersister(val ref: FirebaseRef) {

    private val log = loggerFor<StatPersister>()

    fun persist(bets: List<BetData>) {
        log.info("Generate and persist stats")
        val statMapper = StatMapper()

        bets.groupBy { it.match!!.number }
                .forEach {
                    log.debug("Persist stat match #${it.value}}")
                    val betData = it.value

                    val stat = statMapper.map(betData)
                    log.trace("Stat object generated: $stat")

                    ref.firebase.child(Collections.newscast)
                            .child("/stats")
                            .child(it.key.toString()).setValue(stat)
                }
    }

}
