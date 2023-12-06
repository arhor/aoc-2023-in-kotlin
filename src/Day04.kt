import java.util.LinkedList
import java.util.Queue

fun main() {
    val data = readInput("Day04").parse()

    fun part1(): Int =
        data.map { it.act.filter(it.win::contains) }
            .filter { it.isNotEmpty() }
            .sumOf { 1 shl (it.size - 1) }

    fun part2(): Int =
        calcScore(
            table = data.associateBy { it.num },
            cards = LinkedList(data),
        )

    part1().println()
    part2().println()
}

private tailrec fun calcScore(table: Map<Int, Card>, cards: Queue<Card>, result: Int = 0): Int =
    if (cards.isEmpty()) {
        result
    } else {
        val card = cards.poll()
        val from = card.num + 1
        val upto = from + card.act.filter(card.win::contains).size - 1

        cards += (from..upto).mapNotNull(table::get)

        calcScore(table, cards, result + 1)
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