fun main() {
    val regexDigits = Regex("[^0-9]")
    val regexChars = Regex("[^a-z]")

    fun parseBags(input: String): List<Bag> {
        val splitedBags = input.split(";")
        val bags = splitedBags.map {
            val cubes = it.split(",").map {
                val color = it.replace(regexChars, "")
                val count = it.replace(regexDigits, "")
                Pair(color, count.toInt())
            }

            val red = cubes.find { it.first == "red" }?.second ?: 0
            val blue = cubes.find { it.first == "blue" }?.second ?: 0
            val green = cubes.find { it.first == "green" }?.second ?: 0
            Bag(red,blue,green)
        }

        return bags
    }


    fun parseGame(input: String): Game {
        val splitted = input.splitToSequence(":")

        val id = splitted.first().replace(regexDigits, "").toInt()
        val bags = parseBags(splitted.last())

        return Game(id, bags)
    }

    fun part1(input: List<String>): Int = input.map { parseGame(it) }.filter { game ->
        game.bags.filter { it.red <= 12 && it.green <= 13 && it.blue <= 14 }.size == game.bags.size
    }.sumOf { it.id }

    fun part2(input: List<String>): Int = input.map { parseGame(it) }.sumOf { game ->
        game.bags.maxOf { it.red } * game.bags.maxOf { it.blue } * game.bags.maxOf { it.green }
    }

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

data class Bag(val red: Int, val blue: Int, val green: Int)
data class Game(val id: Int, val bags: List<Bag>)