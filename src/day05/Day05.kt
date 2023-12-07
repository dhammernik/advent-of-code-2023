package day05

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import println
import readInput
import utils.chunked

data class Mapper(val name: String, val ranges: List<Pair<LongRange, LongRange>>)

fun main() {

    fun parseSeeds(input: String): List<Long> {
        return input.substringAfter("seeds: ")
            .split(" ").map { it.toLong() }
    }

    fun parseRangeSeeds(input: String): List<LongRange> {
        return input.substringAfter("seeds: ")
            .split(" ")
            .chunked(2)
            .map {
                val first = it.first().toLong()
                LongRange(first, first + it.last().toLong() - 1)
            }
    }

    fun parseMappers(input: List<String>): List<Mapper> {
        val chunks = input.chunked { it.isEmpty() }
            .filter { it.isNotEmpty() && it.first().isNotBlank() }

        val mappers = chunks.map {
            val name = it.first().substringBefore(" map:")
            val ranges = it.subList(1, it.size).map { range ->
                val (dest, source, length) = range.split(" ").map { num -> num.toLong() }
                Pair(LongRange(source, source + length - 1), LongRange(dest, dest + length - 1))
            }

            Mapper(name, ranges)
        }

        return mappers
    }

    fun part1(input: List<String>): Long {
        val mappers = parseMappers(input.subList(1, input.size))
        return parseSeeds(input.first()).minOf { seed ->
            mappers.fold(seed) { res, mapper ->
                mapper.ranges.filter { range -> range.first.contains(res) }
                    .map { range -> range.second.first + (res - range.first.first)}
                    .firstOrNull() ?: res
            }
        }
    }

    fun getMinimum(source: LongRange, mappers: List<Mapper>): Long {
        return source.minOf { seed ->
            var res = seed
            mappers.forEach { mapper ->
                res = mapper.ranges.filter { range -> range.first.contains(res) }
                    .firstNotNullOfOrNull { range -> range.second.first + (res - range.first.first) }
                    ?: res

            }
            res
        }
    }

    fun part2(input: List<String>): Long {
        val mappers = parseMappers(input.subList(1, input.size))
        return runBlocking<Long> {
            val jobs = parseRangeSeeds(input.first()).map { seeds ->
                async(Dispatchers.IO) {
                    getMinimum(seeds, mappers)
                }
            }
            jobs.awaitAll().min()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day05/Day05_test")
    part1(testInput).println()
    //part2(testInput).println()
    check(part1(testInput) == 35L)
    check(part2(testInput) == 46L)

    val input = readInput("day05/Day05")
    part1(input).println()
    part2(input).println()
}