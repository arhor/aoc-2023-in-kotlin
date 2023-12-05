fun main() {
    val input = readInput("Day04")

    fun part1(): Int =
        parse(input)
            .map { it.act.filter(it.win::contains) }
            .filter { it.isNotEmpty() }
            .sumOf { 1 shl (it.size - 1) }

    fun part2(): Int {
        val table = parse(input).associateBy { it.num }

        return 0
    }

    part1().println()
    part2().println()
}

private data class Card(val num: Int, val win: Set<Int>, val act: Set<Int>)

private fun parse(input: List<String>): List<Card> = input.mapNotNull(pattern::find).map {
    Card(
        num = it.groups["num"]!!.value.toInt(),
        win = it.groups["win"]!!.value.numbers,
        act = it.groups["act"]!!.value.numbers,
    )
}

private val pattern = "^Card( )+(?<num>\\d+): (?<win>.+) \\| (?<act>.+)$".toRegex()
private val String.numbers
    get() = this.split(" ").map(String::trim).filter(String::isNotEmpty).map(String::toInt).toSet()