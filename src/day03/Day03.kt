package day03

import println
import readInput
import java.util.*

fun main() {
    data class Symbol(val id: UUID, val char: Char, val position: Pair<Int, Int>)
    data class Gear(val id: Int, val position: Pair<Int, IntRange>, val symbol: Symbol?)

    fun parseSymbols(input: List<String>): List<Symbol> {
        val symbols = mutableListOf<Symbol>()
        input.forEachIndexed { index, line ->
            val ranges = Regex("[^\\w[!.]]").findAll(line)
            ranges.forEach {
                symbols.add(
                    Symbol(UUID.randomUUID(), it.value.first(), Pair(index, it.range.first))
                )
            }
        }

        return symbols
    }

    fun parseGears(input: List<String>): List<Gear> {
        val symbols = parseSymbols(input)

        return input.mapIndexed { index, line ->
            val gears = Regex("[0-9]+").findAll(line).map { result ->
                val newRange = IntRange(result.range.first -1, result.range.last + 1)
                val symbol = symbols.filter {
                    it.position.first == index || it.position.first == index - 1 || it.position.first == index + 1
                }.firstOrNull {
                    newRange.contains(it.position.second)
                }

                Gear(line.substring(result.range).toInt(), Pair(index, result.range), symbol)
            }.toList()

            gears
        }.flatten()
    }

    fun part1(input: List<String>): Int {
        val gears = parseGears(input)
        return gears.filter { it.symbol != null }.sumOf { it.id }
    }

    fun part2(input: List<String>): Int {
        val gears = parseGears(input)
        return gears.filter { it.symbol?.char == '*' }
            .groupBy { it.symbol?.id }
            .filter { it.value.size > 1 }
            .mapValues { it.value.fold(1) {
                                          total, next -> total * next.id }
            }
            .map { it.value }
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day03/Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("day03/Day03")
    part1(input).println()
    part2(input).println()
}

