package org.ojacquemart.eurobets.firebase.initdb.raw

import org.ojacquemart.eurobets.util.JsonFileLoader

class RawFixturesJsonFileLoader {

    val FILE_NAME = "theguardian-fixtures.json"

    fun load(): RawFixtures {
        return JsonFileLoader<RawFixtures>().load(FILE_NAME, RawFixtures::class.java)
    }

}