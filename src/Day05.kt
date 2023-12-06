fun main() {
    data class Value(val type: String, val data: List<Long>)
    data class Range(val rangeInto: Long, val rangeFrom: Long, val rangeSize: Long)
    data class Mapping(val from: String, val into: String, val data: List<Range>)
    data class Model(val initial: Value, val mapping: List<Mapping>)

    val pattern = "^(?<from>\\w+)-to-(?<into>\\w+) map:$".toRegex()

    val data = readInput("Day05").split("").destruct().let { (head, tail) ->
        Model(
            initial = head.single().split(":").let { (type, data) ->
                Value(
                    type = type,
                    data = data.numbers()
                )
            },
            mapping = tail.map { it.destruct() }.map { (head, tail) ->
                pattern.find(head)!!.groups.let { match ->
                    Mapping(
                        from = match["from"]!!.value,
                        into = match["into"]!!.value,
                        data = tail.map(String::numbers).map { (one, two, thr) ->
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

    println(data)

    fun part1(): Int = 0
    fun part2(): Int = 0

    part1().println()
    part2().println()
}
