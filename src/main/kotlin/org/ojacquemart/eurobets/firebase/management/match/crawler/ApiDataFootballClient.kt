package org.ojacquemart.eurobets.firebase.management.match.crawler

import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import java.util.*

class ApiDataFootballClient {

    fun getFixtures(): List<Fixture> {
        val headers = LinkedMultiValueMap<String, String>();
        addXAuthTokenIfPresent(headers)

        val entity = HttpEntity<String>(headers);
        val template = RestTemplate()
        val response = template.exchange(API_FOOT_URL, HttpMethod.GET, entity, Fixtures::class.java)

        return response.body!!.fixtures
    }

    private fun addXAuthTokenIfPresent(headers: LinkedMultiValueMap<String, String>) {
        val maybeXAuthToken = getXAuthToken()
        if (maybeXAuthToken.isPresent) {
            headers.add(HEADER_XAUTH_TOKEN, maybeXAuthToken.get());
        }
    }

    fun getXAuthToken() = Optional.ofNullable(System.getProperty("apidatafootball.token"))

    companion object {
        val API_FOOT_URL = "http://api.football-data.org/v1/soccerseasons/424/fixtures"
        val HEADER_XAUTH_TOKEN = "X-Auth-Token"
    }

}