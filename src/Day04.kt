import java.util.LinkedList

fun main() {
    val data = readInput("Day04").parse()

    fun part1(): Int =
        data.map { it.act.filter(it.win::contains) }
            .filter { it.isNotEmpty() }
            .sumOf { 1 shl (it.size - 1) }

    fun part2(): Int {
        val table = data.associateBy { it.num }
        val outer = noRecursive(table, data)

        return outer
    }

    part1().println()
    part2().println()
}

private fun noRecursive(table: Map<Int, Card>, cards: List<Card>): Int {
    val copies = LinkedList(cards)
    var result = 0

    while (copies.isNotEmpty()) {
        val card = copies.pop().also { result++ }
        val matchings = card.act.filter(card.win::contains)

        val from = card.num + 1
        val upto = from + matchings.size - 1

        copies += (from..upto).mapNotNull(table::get)
    }
    return result
}

private data class Card(val num: Int, val win: Set<Int>, val act: Set<Int>)

private fun List<String>.parse(): List<Card> = mapNotNull(pattern::find).map {
    Card(
        num = it.groups["num"]!!.value.toInt(),
        win = it.groups["win"]!!.value.numbers,
        act = it.groups["act"]!!.value.numbers,
    )
}

private val pattern = "^Card( )+(?<num>\\d+): (?<win>.+) \\| (?<act>.+)$".toRegex()
private val String.numbers
    get() = this.split(" ").map(String::trim).filter(String::isNotEmpty).map(String::toInt).toSet()