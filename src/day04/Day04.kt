package day04

import println
import readInput

fun main() {
    data class Card(val id: Int, val winning: List<Int>, val stack: List<Int>, val matching: Set<Int>)

    fun parseNumbers(input: String): List<Int> {
        return input.split(" ").filter { it.isNotBlank() }.map { it.toInt() }
    }

    fun parseCard(input: String): Card {
        val splited = input.splitToSequence(":")
        val id = splited.first().replace(Regex("[^0-9]"), "").toInt()
        val numbers = splited.last().splitToSequence("|")
        val winning = parseNumbers(numbers.first())
        val current = parseNumbers(numbers.last())
        return Card(id, winning, current, winning.intersect(current.toSet()))
    }

    fun part1(input: List<String>): Int {
        return input.map { parseCard(it) }.sumOf { card ->
            card.matching.foldIndexed(0) { idx, sum, _ ->
                if (idx == 0) 1 + sum else sum + sum
            }.toInt()
        }
    }

    fun part2(input: List<String>): Int {
        val stack = mutableListOf<MutableList<Card>>()
        val cards = input.map { parseCard(it) }

        cards.forEach { card ->
            stack.add(mutableListOf(card))
        }

        stack.forEachIndexed { index, list ->
            list.forEach { card ->
                val startIdx = index + 1
                if (startIdx < stack.size) {
                    for (i in startIdx..index + card.matching.size) {
                        val ll = stack.get(i)
                        val first = ll.first()
                        ll.add(first)
                    }
                }
            }
        }

        return stack.sumOf { it.size }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day04/Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("day04/Day04")
    part1(input).println()
    part2(input).println()
}