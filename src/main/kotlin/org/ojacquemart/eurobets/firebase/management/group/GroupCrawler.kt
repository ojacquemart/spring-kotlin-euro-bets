package org.ojacquemart.eurobets.firebase.management.group

import org.ojacquemart.eurobets.firebase.initdb.group.v2.FixturesV2
import org.ojacquemart.eurobets.lang.loggerFor
import org.springframework.web.client.RestTemplate

class GroupCrawler {

    private val log = loggerFor<GroupCrawler>()

    fun crawl(): FixturesV2 {
        log.info("Crawling groups from $GUARDIAN_API_URL")

        val restTemplate = RestTemplate()

        return restTemplate.getForObject(GUARDIAN_API_URL, FixturesV2::class.java)
    }

    companion object {
        val GUARDIAN_API_URL = "https://interactive.guim.co.uk/docsdata/1EAeb9sKV4xbUivz9XkQYpMc5MPxgmuRu7XCBA-b745E.json"
    }

}