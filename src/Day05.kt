import kotlin.time.measureTime

fun main() {
    data class Value(
        val type: String,
        val data: List<Long>,
    )

    data class Range(
        val rangeFrom: LongRange,
        val rangeInto: LongRange,
    ) {
        constructor(source: Long, target: Long, size: Long) : this(
            rangeFrom = source..<(source + size),
            rangeInto = target..<(target + size),
        )
    }

    data class Mapping(
        val from: String,
        val into: String,
        val ranges: List<Range>,
    )

    data class Model(
        val initial: Value,
        val mappings: List<Mapping>,
    )

    val pattern = "^(?<from>\\w+)-to-(?<into>\\w+) map:$".toRegex()

    val model = readInput("Day05").split("").destruct().let { (head, tail) ->
        Model(
            initial = head.single().split(":").let { (type, data) ->
                Value(
                    type = type,
                    data = data.numbers()
                )
            },
            mappings = tail.map { it.destruct() }.map { (head, tail) ->
                pattern.find(head)!!.groups.let { match ->
                    Mapping(
                        from = match["from"]!!.value,
                        into = match["into"]!!.value,
                        ranges = tail
                            .map(String::numbers)
                            .map { (target, source, size) -> Range(source, target, size) }
                            .sortedBy { it.rangeFrom.first }
                    )
                }
            }
        )
    }

    operator fun LongRange.get(index: Long) = start + (step * index)
    fun LongRange.fastIndexOf(value: Long) = (value - start) / step

    fun Iterable<Long>.calculate(): Long = minOf { value ->
        var result = value
        for (mapping in model.mappings) {
            result = mapping.ranges.find { result in it.rangeFrom }
                ?.let { it.rangeInto[it.rangeFrom.fastIndexOf(result)] }
                ?: result
        }
        result
    }

    fun part1(): Long = model.initial.data.calculate()

    fun part2(): Long {
        return model.initial.data.chunked(2)
            .map { (from, size) -> from..<(from + size) }
            .sortedBy { it.first }
            .stream()
            .parallel()
            .mapToLong { it.calculate() }
            .min()
            .asLong
    }

    measureTime { part1().println() }.also { println("Operation took: $it") }
    measureTime { part2().println() }.also { println("Operation took: $it") }
}
