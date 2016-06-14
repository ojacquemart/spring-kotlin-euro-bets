package org.ojacquemart.eurobets.firebase.management.group

import org.ojacquemart.eurobets.firebase.Collections
import org.ojacquemart.eurobets.firebase.config.FirebaseRef
import org.ojacquemart.eurobets.lang.loggerFor
import org.springframework.stereotype.Component

@Component
open class GroupsUpdater(val ref: FirebaseRef) {

    private val log = loggerFor<GroupsUpdater>()

    fun update() {
        log.info("Try to update groups...")

        val groupCrawler = GroupCrawler()
        val groups = groupCrawler.crawl()

        log.info("Persist groups...")
        ref.firebase.child(Collections.groups).setValue(groups)
    }

}