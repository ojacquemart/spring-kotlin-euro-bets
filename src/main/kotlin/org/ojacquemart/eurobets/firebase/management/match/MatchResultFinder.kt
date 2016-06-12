package org.ojacquemart.eurobets.firebase.management.match

import org.ojacquemart.eurobets.firebase.management.match.crawler.Crawler
import org.ojacquemart.eurobets.lang.loggerFor

class MatchResultFinder {

    private val log = loggerFor<MatchResultFinder>()

    fun find(match: Match): MatchWithFixture? {
        log.info("Crawl for playing matches")

        val fixtures = CRAWLER.crawl()
        val playingOrFinishedFixtures = fixtures.filter { it.isFinished() }

        val maybeFixture = playingOrFinishedFixtures.find { it.matches(match) }
        if (maybeFixture != null) {
            return MatchWithFixture(match, maybeFixture)
        }

        return null
    }

    companion object {
        val CRAWLER = Crawler()
    }
}