package org.ojacquemart.eurobets.firebase.initdb

import org.ojacquemart.eurobets.firebase.initdb.fixture.Fixture
import org.ojacquemart.eurobets.firebase.initdb.group.Group

data class SheetData(val fixtures: List<Fixture>, val groups: List<Group>) {
}