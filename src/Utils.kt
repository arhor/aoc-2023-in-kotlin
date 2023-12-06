import java.math.BigInteger
import java.security.MessageDigest
import java.util.LinkedList
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.abs

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)


data class Point(val x: Int, val y: Int) : Comparable<Point> {

    override fun compareTo(other: Point): Int = COMPARATOR.compare(this, other)

    fun adjacentPoints(diagonal: Boolean = true) = sequence {
        yield(value = copy(y = y + 1))
        if (diagonal) yield(value = copy(x = x + 1, y = y + 1))
        yield(value = copy(x = x + 1))
        if (diagonal) yield(value = copy(x = x + 1, y = y - 1))
        yield(value = copy(y = y - 1))
        if (diagonal) yield(value = copy(x = x - 1, y = y - 1))
        yield(value = copy(x = x - 1))
        if (diagonal) yield(value = copy(x = x - 1, y = y + 1))
    }

    fun enclosedTo(other: Point): Point = when {
        x == other.x && y == other.y -> this

        x == other.x && y < other.y -> copy(y = y + 1)
        y == other.y && x < other.x -> copy(x = x + 1)

        y < other.y && x < other.x -> copy(y = y + 1, x = x + 1)

        x == other.x -> copy(y = y - 1)
        y == other.y -> copy(x = x - 1)

        y < other.y -> copy(y = y + 1, x = x - 1)
        x < other.x -> copy(y = y - 1, x = x + 1)

        else -> copy(y = y - 1, x = x - 1)
    }

    fun manhattanDistanceTo(other: Point) = abs(x - other.x) + abs(y - other.y)

    companion object {
        val COMPARATOR: Comparator<Point> = compareBy({ it.y }, { it.x })
    }
}

fun <T> List<T>.split(separator: T): List<List<T>> {
    val result = LinkedList<MutableList<T>>()
    val newRow = { LinkedList<T>().also(result::add) }

    for (it in this) {
        if (it == separator) {
            newRow()
        } else {
            (result.lastOrNull() ?: newRow()).add(it)
        }
    }
    return result
}
