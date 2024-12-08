package at.cath.day08

import at.cath.readInput

typealias Coordinate = Pair<Int, Int>
typealias Grid = List<String>

operator fun Coordinate.plus(other: Coordinate) = (first + other.first to second + other.second)
operator fun Coordinate.minus(other: Coordinate) = (first - other.first to second - other.second)

fun main() {
    val input = readInput("Day08")
    val (maxX, maxY) = input.size to input[0].length

    fun inBounds(point: Coordinate) = point.let { (x, y) -> x in 0 until maxX && y in 0 until maxY }

    fun checkSignal(grid: Grid, harmonic: Boolean) = buildSet {
        val tracker = mutableMapOf<Char, MutableList<Coordinate>>()

        grid.forEachIndexed { x, s ->
            s.forEachIndexed rowLoop@{ y, c ->
                if (c == '.') return@rowLoop
                val loc = x to y

                tracker[c]?.forEach { prev ->
                    val slope = loc - prev
                    var (extendA, extendB) = loc + slope to prev - slope

                    if (harmonic) {
                        add(loc)
                        add(prev)
                    }

                    while (inBounds(extendA) || inBounds(extendB)) {
                        if (inBounds(extendA)) add(extendA)
                        if (inBounds(extendB)) add(extendB)
                        if (!harmonic) break

                        extendA += slope
                        extendB -= slope
                    }
                }
                tracker.computeIfAbsent(c) { mutableListOf() }.add(loc)
            }
        }
    }.size

    println("Part 1: ${checkSignal(input, false)}")
    println("Part 2: ${checkSignal(input, true)}")
}