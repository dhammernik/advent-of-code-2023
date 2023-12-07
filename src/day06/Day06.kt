package day06

import println
import readInput

data class Race(val duration: Long, val recordDistance: Long)
fun main() {
    fun parseRaces(input: List<String>): List<Race> {
        val durations = input.first().substringAfter(":").split(" ").filter { it.isNotEmpty() }.map { it.toLong() }
        val records = input.last().substringAfter(":").split(" ").filter { it.isNotEmpty() }.map { it.toLong() }
        return durations.mapIndexed { idx, duration -> Race(duration, records[idx])}
    }

    fun parseRace(input: List<String>): Race {
        val duration = input.first().substringAfter(":").replace(" ", "").toLong()
        val record = input.last().substringAfter(":").replace(" ", "").toLong()
        return Race(duration, record)
    }

    fun calculate(race: Race): List<Long> {
        return (0..race.duration).map {
            step -> (race.duration - step) * step
        }.filter { distance -> distance > race.recordDistance }
    }

    fun part1(input: List<String>): Int {
        val races = parseRaces(input)
        return races.map { race -> calculate(race) }.fold(1) { total, distances ->
            total * distances.size
        }
    }

    fun part2(input: List<String>): Int {
        val race = parseRace(input)
        return calculate(race).size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day06/Day06_test")
    part1(testInput).println()
    part2(testInput).println()
    check(part1(testInput) == 288)
    check(part2(testInput) == 71503)

    val input = readInput("day06/Day06")
    part1(input).println()
    part2(input).println()
}