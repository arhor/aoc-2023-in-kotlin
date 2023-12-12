import java.util.Objects
import java.util.stream.LongStream
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

    operator fun LongRange.get(index: Long): Long {
        Objects.checkIndex(index, (endInclusive - start) / step)
        return start + (step * index)
    }

    fun LongRange.fastIndexOf(value: Long): Long {
        require(value in this) { "Value $value is not in the range $this" }
        return (value - start) / step
    }

    fun part1(): Long = model.initial.data.minOf { value ->
        var result = value
        for (mapping in model.mappings) {
            result = mapping.ranges.find { result in it.rangeFrom }
                ?.let { it.rangeInto[ it.rangeFrom.fastIndexOf(result) ] }
                ?: result
        }
        result
    }

    fun part2(): Long {
        return 0
    }

    measureTime { part1().println() }.also { println("Operation took: $it") }
    measureTime { part2().println() }.also { println("Operation took: $it") }
}
