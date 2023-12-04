fun main() {
    fun part1(input: List<String>): Int = parseInput(input)
        .filter { it.subsets.all(Subset::valid) }
        .sumOf { it.id }

    fun part2(input: List<String>): Int = parseInput(input)
        .map { it.subsets }
        .sumOf { it.maxOf(Subset::r) * it.maxOf(Subset::g) * it.maxOf(Subset::b) }

    val input = readInput("Day02")

    part1(input).println()
    part2(input).println()
}

private fun parseInput(input: List<String>): Sequence<Game> {
    return input.asSequence()
        .mapNotNull { pattern.find(it) }
        .map { it.destructured }
        .map { (num, seq) ->
            Game(
                id = num.toInt(),
                subsets = parseSubset(seq)
            )
        }
}

private fun parseSubset(text: String): List<Subset> =
    text.split(";")
        .map(String::trim)
        .map { data ->
            data.split(",")
                .map(String::trim)
                .associate {
                    it.split(" ").let { (value, color) ->
                        color to value
                    }
                }
        }
        .map {
            Subset(
                r = it["red"]?.toInt() ?: 0,
                g = it["green"]?.toInt() ?: 0,
                b = it["blue"]?.toInt() ?: 0,
            )
        }

private data class Game(val id: Int, val subsets: List<Subset>)
private data class Subset(val r: Int, val g: Int, val b: Int)

private val pattern = "^Game (?<num>\\d+): (?<seq>.+)$".toRegex()
private val Subset.valid: Boolean
    get() {
        return r <= 12
            && g <= 13
            && b <= 14
    }