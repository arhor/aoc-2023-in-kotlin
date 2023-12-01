fun main() {
    fun calibrationSum(data: List<String>, parser: (String) -> List<Int>): Int =
        data.asSequence()
            .map { parser.invoke(it) }
            .map { "${it.first()}${it.last()}" }
            .sumOf { it.toInt() }

    fun part1(input: List<String>): Int = calibrationSum(input) { line ->
        line.filter(Char::isDigit)
            .map(Char::digitToInt)
    }

    fun part2(input: List<String>): Int = calibrationSum(input) { line ->
        DIGIT_PATTERN.findAll(line)
            .map { it.groups.filterNotNull().last().value }
            .map { c -> DIGIT_MAPPING[c] ?: c }
            .map { it.toInt() }
            .toList()
    }

    val input = readInput("Day01")

    part1(input).println()
    part2(input).println()
}


val DIGIT_PATTERN = Regex("(?=(zero)|(one)|(two)|(three)|(four)|(five)|(six)|(seven)|(eight)|(nine)|([0-9]))")

//@formatter:off
val DIGIT_MAPPING = mapOf(
    "zero"  to "0",
    "one"   to "1",
    "two"   to "2",
    "three" to "3",
    "four"  to "4",
    "five"  to "5",
    "six"   to "6",
    "seven" to "7",
    "eight" to "8",
    "nine"  to "9",
)
//@formatter:on
