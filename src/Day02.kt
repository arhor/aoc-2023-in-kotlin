fun main() {
    fun part1(input: List<String>): Int {
        return input.asSequence()
            .mapNotNull { pattern.find(it) }
            .map { it.destructured }
            .map { (id, seq) -> Game(id = id.toInt(), subsets = pasrseSubset(seq)) }
            .filter { it.subsets.all(Subset::valid) }
            .sumOf { it.id }
    }

    fun part2(input: List<String>): Int {
        return input.asSequence()
            .mapNotNull { pattern.find(it) }
            .map { it.destructured }
            .map { (id, seq) -> Game(id = id.toInt(), subsets = pasrseSubset(seq)) }
            .sumOf { it.subsets.maxOf { it.r } * it.subsets.maxOf { it.g } * it.subsets.maxOf { it.b } }
    }

    val input = readInput("Day02")

    part1(input).println()
    part2(input).println()
}

private fun pasrseSubset(text: String): List<Subset> {
    return text.split(";").map(String::trim).map { single ->
        single.split(",").map(String::trim).associate {
            it.split(" ").let { (value, color) ->
                color to value
            }
        }
    }.map {
        Subset(
            r = it["red"]?.toInt() ?: 0,
            g = it["green"]?.toInt() ?: 0,
            b = it["blue"]?.toInt() ?: 0,
        )
    }
}

private data class Game(val id: Int, val subsets: List<Subset>)
private data class Subset(val r: Int, val g: Int, val b: Int) {
    val valid: Boolean
        get() {
            return r <= 12
                && g <= 13
                && b <= 14
        }
}

private val pattern = "^Game (?<num>\\d+): (?<seq>.+)$".toRegex()