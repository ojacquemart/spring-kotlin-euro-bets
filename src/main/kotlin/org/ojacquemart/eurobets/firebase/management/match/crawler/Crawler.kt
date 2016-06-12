package org.ojacquemart.eurobets.firebase.management.match.crawler

import com.google.common.base.Supplier
import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import org.ojacquemart.eurobets.lang.loggerFor
import org.springframework.web.client.RestTemplate
import java.util.concurrent.TimeUnit

class Crawler() {

    private val log = loggerFor<Crawler>()

    fun crawl(): List<Fixture> {
        log.info("Get all fixtures...")

        return loader.get(ANY_KEY)
    }

    companion object {
        val ANY_KEY = "any"
        val API_FOOT_URL = "http://api.football-data.org/v1/soccerseasons/424/fixtures"

        val DURATION_VALUE = 1L
        val DURATION_TIME_UNIT = TimeUnit.SECONDS

        private val log = loggerFor<Crawler>()

        val supplier = Supplier<List<org.ojacquemart.eurobets.firebase.management.match.crawler.Fixture>> {
            val template = RestTemplate()
            val result = template.getForObject(API_FOOT_URL, Fixtures::class.java)
            log.trace("Fixtures fetch: $result")

            result.fixtures
        }

        val cacheLoader = CacheLoader.from(supplier)

        val loader = CacheBuilder.newBuilder()
                .expireAfterWrite(DURATION_VALUE, DURATION_TIME_UNIT)
                .build(cacheLoader)
    }

}