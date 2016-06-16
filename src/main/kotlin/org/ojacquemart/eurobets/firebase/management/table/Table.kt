package org.ojacquemart.eurobets.firebase.management.table

data class Table(val table: List<TableRow>,
                 val nbRows: Int,
                 val lastPosition: Int,
                 val podium: Podium)