import kotlin.math.abs

fun main() {
    fun part1(listA: List<Int>, listB: List<Int>): Int =
        listA.sorted().zip(listB.sorted()) { a, b -> abs(a - b) }.sum()

    // todo: you could lookup table the occurrences but whatever
    fun part2(listA: List<Int>, listB: List<Int>): Int =
        listA.sumOf { a -> a * listB.count { it == a } }

    val (listA, listB) = readInput("Day01")
        .map {
            it.split("   ")
                .map(String::toInt).let { (a, b) -> a to b }
        }
        .unzip()

    println(part1(listA, listB))
    println(part2(listA, listB))
}
