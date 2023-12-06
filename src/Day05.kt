fun main() {
    data class Value(val type: String, val data: List<Long>)
    data class Range(val rangeInto: Long, val rangeFrom: Long, val rangeSize: Long)
    data class Mapping(val from: String, val into: String, val ranges: List<Range>)
    data class Model(val initial: Value, val mappings: List<Mapping>)

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
                        ranges = tail.map(String::numbers).map { (one, two, thr) ->
                            Range(
                                rangeInto = one,
                                rangeFrom = two,
                                rangeSize = thr,
                            )
                        }
                    )
                }
            }
        )
    }

    fun part1(): Int {
        var result = model.initial.data
        for (mapping in model.mappings) {
            println(mapping)
        }
        return 0
    }
    fun part2(): Int = 0

    part1().println()
    part2().println()
}
