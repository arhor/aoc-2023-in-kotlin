fun main() {
    data class Value(val type: String, val data: List<Long>)
    data class Descriptor(val rangeInto: Long, val rangeFrom: Long, val rangeSize: Long)
    data class Mapping(val from: String, val into: String, val data: List<Descriptor>)
    data class Model(val initial: Value, val mapping: List<Mapping>)

    val data = readInput("Day05").split("").destruct().let { (head, tail) ->
        Model(
            initial = head.single().split(":").let { (type, data) ->
                Value(
                    type = type,
                    data = data.numbers()
                )
            },
            mapping = tail.map { line ->
                pattern.find(line.first())!!.groups.let { match ->
                    Mapping(
                        from = match["from"]!!.value,
                        into = match["into"]!!.value,
                        data = line.drop(1).map(String::numbers).map { (one, two, thr) ->
                            Descriptor(
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

private val pattern = "^(?<from>\\w+)-to-(?<into>\\w+) map:$".toRegex()
