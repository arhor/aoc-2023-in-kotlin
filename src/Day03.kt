fun main() {
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

    fun part2(): Int = TODO()

    part1().println()
    part2().println()
}

private val pattern = "\\d+".toRegex()
