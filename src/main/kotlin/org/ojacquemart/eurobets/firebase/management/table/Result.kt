package org.ojacquemart.eurobets.firebase.management.table

enum class Result constructor(val id: Int) {
    UNDEFINED(-1),
    PERFECT(0),
    GOOD_GAP(3),
    GOOD(1),
    BAD(2)
    ;

    fun hasReportedPoints(): Boolean {
        return id != UNDEFINED.id && id != BAD.id
    }

}