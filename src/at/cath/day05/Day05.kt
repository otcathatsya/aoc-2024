package at.cath.day05

import at.cath.readInput

fun main() {
    fun sumMiddle(
        verificationList: List<List<Int>>,
        map: Map<Pair<Int, Int>, Boolean>,
        wrongOnly: Boolean
    ): Int = verificationList.sumOf { row ->
        val sortedRow = row.sortedWith { a, b -> if (map[a to b] == true) -1 else 1 }
        if ((row == sortedRow) != wrongOnly) sortedRow[row.size / 2] else 0
    }

    fun part1(verificationList: List<List<Int>>, map: Map<Pair<Int, Int>, Boolean>) =
        sumMiddle(verificationList, map, wrongOnly = false)

    fun part2(verificationList: List<List<Int>>, map: Map<Pair<Int, Int>, Boolean>) =
        sumMiddle(verificationList, map, wrongOnly = true)

    val (map, verificationList) = readInput("Day05").fold(Pair(mutableMapOf<Pair<Int, Int>, Boolean>(), mutableListOf<MutableList<Int>>())) { acc, line ->
        if (line.isEmpty()) acc else {
            val (map, list) = acc
            if ('|' in line) {
                val (a, b) = line.split("|").map(String::toInt)
                map[a to b] = true
                map[b to a] = false
            } else {
                list.add(line.split(",").map(String::toInt).toMutableList())
            }
            acc
        }
    }

    println(part1(verificationList, map))
    println(part2(verificationList, map))
}
