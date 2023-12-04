import java.util.LinkedList

fun main() {
    data class ValueHolder(val value: Int, val gear: Point)

    val input = readInput("Day03")

    fun part1(): Int {
        var result = 0
        lines@ for ((index, line) in input.withIndex()) {
            matching@ for (matching in pattern.findAll(line)) {
                val yFrom = (index - 1).coerceAtLeast(0)
                val yUpto = (index + 1).coerceAtMost(input.size - 1)

                val xFrom = (matching.range.first - 1).coerceAtLeast(0)
                val xUpto = (matching.range.last + 1).coerceAtMost(line.length - 1)

                for (y in (yFrom..yUpto)) {
                    for (x in (xFrom..xUpto)) {
                        val value = input[y][x]
                        if (!value.isDigit() && value != '.') {
                            result += matching.value.toInt()
                            continue@matching
                        }
                    }
                }
            }
        }
        return result
    }

    fun part2(): Int {
        var result = LinkedList<ValueHolder>()
        lines@ for ((index, line) in input.withIndex()) {
            matching@ for (matching in pattern.findAll(line)) {
                val yFrom = (index - 1).coerceAtLeast(0)
                val yUpto = (index + 1).coerceAtMost(input.size - 1)
                val xFrom = (matching.range.first - 1).coerceAtLeast(0)
                val xUpto = (matching.range.last + 1).coerceAtMost(line.length - 1)
                var valid = false
                var point = Point(-1, -1)
                for (y in (yFrom..yUpto)) {
                    for (x in (xFrom..xUpto)) {
                        val value = input[y][x]
                        if (!value.isDigit() && value != '.') {
                            valid = true
                            if (value == '*') {
                                point = Point(x, y)
                            }
                        }
                    }
                }
                if (valid) {
                    result += ValueHolder(matching.value.toInt(), point)
                }
            }
        }
        return result
            .groupBy { it.gear }
            .filter { it.key != Point(-1, -1) && it.value.size == 2 }
            .map { it.value.map { it.value }.fold(1) { prev, next -> prev * next } }
            .sum()
    }

    part1().println()
    part2().println()
}

private val pattern = "\\d+".toRegex()
