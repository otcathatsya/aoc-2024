package at.cath.day09

import at.cath.readInput

fun main() {
    val input = readInput("Day09").first().map { it.digitToInt() }

    fun decompose(prevSpace: Int, id: Int, space: Int): Long =
        (0 until space).sumOf { (prevSpace + it) * id.toLong() }

    var agg: Long = 0
    val revUsedIndices = mutableSetOf<Int>()
    var prevSpace = 0
    var frontId = 0
    val maxId = (input.size - 1) / 2

    for (id in input.indices) {
        val space = input[id]
        val isEven = id % 2 == 0

        if (isEven && id != 0) frontId++

        if (isEven && id !in revUsedIndices) {
            agg += decompose(prevSpace, frontId, space)
            revUsedIndices.add(id)
            prevSpace += space
        } else {
            // Pull in from back
            var revSpaceReq = space
            var revId = maxId

            for (i in input.size - 1 downTo frontId) {
                if (i % 2 != 0) continue

                val revSpace = input[i]
                if (revSpace <= revSpaceReq && revSpace != 0 && i !in revUsedIndices) {
                    agg += decompose(prevSpace, revId, revSpace)
                    prevSpace += revSpace
                    revSpaceReq -= revSpace
                    revUsedIndices.add(i)
                }
                revId--
            }
            prevSpace += revSpaceReq
        }
    }

    // too lazy to go back and map part1
    println("Result: $agg")
}