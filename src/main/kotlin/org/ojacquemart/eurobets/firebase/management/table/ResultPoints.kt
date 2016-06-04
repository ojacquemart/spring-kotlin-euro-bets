package org.ojacquemart.eurobets.firebase.management.table

data class ResultPoints(val result: Result, val points: Int) {
    companion object {
        fun perfect(points: Int) = ResultPoints(Result.PERFECT, points)
        fun good(points: Int) = ResultPoints(Result.GOOD, points)
        fun bad() = bad(0)
        fun bad(points: Int) = ResultPoints(Result.BAD, points)
    }
}