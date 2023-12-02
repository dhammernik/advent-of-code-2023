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

    fun part1(input: List<String>): Int {
        val games = input.map { parseGame(it) }
        val filtered = games.filter { game ->
            val bagSize = game.bags.size
            val remaining = game.bags.filter { bag ->
                bag.red <= 12 && bag.green <= 13 && bag.blue <= 14
            }

            remaining.size == bagSize
        }

        return filtered.sumOf { it.id }
    }

    fun part2(input: List<String>): Int {
        val games = input.map { parseGame(it) }
        val products = games.map { game ->
            var red = 0
            var green = 0
            var blue = 0

            game.bags.forEach {
                if (it.red > red) {
                    red = it.red
                }

                if (it.blue > blue) {
                    blue = it.blue
                }

                if (it.green > green) {
                    green = it.green
                }
            }

            red * blue * green
        }

        return products.sum()
    }

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

data class Bag(val red: Int, val blue: Int, val green: Int)
data class Game(val id: Int, val bags: List<Bag>)