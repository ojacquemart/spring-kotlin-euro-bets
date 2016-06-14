package org.ojacquemart.eurobets.firebase.management.group

import org.ojacquemart.eurobets.firebase.initdb.group.Group
import org.ojacquemart.eurobets.firebase.initdb.group.GroupConverter
import org.ojacquemart.eurobets.firebase.initdb.raw.RawFixtures
import org.ojacquemart.eurobets.lang.loggerFor
import org.springframework.web.client.RestTemplate

class GroupCrawler {

    private val log = loggerFor<GroupCrawler>()

    fun crawl(): List<Group> {
        log.info("Crawling groups from $GUARDIAN_API_URL")

        val restTemplate = RestTemplate()
        val rawFixtures = restTemplate.getForObject(GUARDIAN_API_URL, RawFixtures::class.java)

        return GroupConverter().getGroups(rawFixtures.sheets)
    }

    companion object {
        val GUARDIAN_API_URL = "https://interactive.guim.co.uk/docsdata/1EAeb9sKV4xbUivz9XkQYpMc5MPxgmuRu7XCBA-b745E.json"
    }

}