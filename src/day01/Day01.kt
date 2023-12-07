package day01

import println
import readInput

fun main() {
    val regexDigits = Regex("[^0-9]")

    fun part1(input: List<String>): Int {
        return input.sumOf {
            val tmp = it.replace(Regex("[^0-9 ]"), "")
            "${tmp.first()}${tmp.last()}".toInt()
        }
    }

    fun String.replace(vararg pairs: Pair<String, String>): String =
        pairs.fold(this) { acc, (old, new) -> acc.replace(old, "${old.first()}${new}${old.last()}") }

    fun part2(input: List<String>): Int {
        val numbers = arrayOf(
            Pair("one", "1"),
            Pair("two", "2"),
            Pair("three", "3"),
            Pair("four", "4"),
            Pair("five", "5"),
            Pair("six", "6"),
            Pair("seven", "7"),
            Pair("eight", "8"),
            Pair("nine", "9"),
        )

        return input.sumOf {
            val mapped = it.replace(*numbers)
            val tmp = mapped.replace(Regex("[^0-9 ]"), "")
            "${tmp.first()}${tmp.last()}".toInt()
        }
    }

    val input = readInput("day01/Day01")
    part1(input).println()
    part2(input).println()
}