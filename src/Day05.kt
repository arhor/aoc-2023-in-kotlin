import java.util.stream.LongStream

fun main() {
    data class Mapping(val from: LongRange, val into: LongRange) {
        constructor(source: Long, target: Long, size: Long) : this(
            from = source..<(source + size),
            into = target..<(target + size),
        )
    }

    data class Model(val initial: List<Long>, val mapping: List<List<Mapping>>)

    val model = readInput("Day05").split("").destruct().let { (head, tail) ->
        Model(
            initial = head.single().split(":").let { (_, data) ->
                data.numbers()
            },
            mapping = tail.map { it.destruct() }.map { (_, data) ->
                data.map { it.numbers() }
                    .map { (target, source, size) -> Mapping(source, target, size) }
                    .sortedBy { it.from.first }
            }
        )
    }

    operator fun LongRange.get(index: Long) = start + (step * index)

    fun LongRange.fastIndexOf(value: Long) = (value - start) / step

    fun sequentialMap(value: Long) = model.mapping.fold(value) { prev, next ->
        next.find { prev in it.from }
            ?.let { it.into[it.from.fastIndexOf(prev)] }
            ?: prev
    }

    fun part1(): Long = model.initial.minOf(::sequentialMap)

    fun part2(): Long = model.initial.chunked(2)
        .map { (from, size) -> from..<(from + size) }
        .stream()
        .parallel()
        .flatMapToLong { LongStream.rangeClosed(it.first, it.last) }
        .map(::sequentialMap)
        .min()
        .asLong

    executeMeasuringTime(::part1, ::part2)
}
