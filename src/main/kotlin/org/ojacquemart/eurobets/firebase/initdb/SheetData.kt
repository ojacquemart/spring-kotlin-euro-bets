package org.ojacquemart.eurobets.firebase.initdb

import org.ojacquemart.eurobets.firebase.initdb.country.Country
import org.ojacquemart.eurobets.firebase.initdb.fixture.Fixture
import org.ojacquemart.eurobets.firebase.initdb.group.Group

data class SheetData(
    val fixtures: Map<String, Fixture>,
    val groups: List<Group>,
    val countries: List<Country>)