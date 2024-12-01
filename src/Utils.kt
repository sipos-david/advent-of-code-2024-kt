import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

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

/**
 * Pretty print a result for a given input file
 */
fun <T1, T2> result(
    name: String,
    path: String,
    solutions: Pair<T1, T2>,
    part1: (List<String>) -> T1,
    part2: (List<String>) -> T2,
) {
    name.println()
    val test = readInput(path)
    partResult(1, solutions.first, part1(test))
    partResult(2, solutions.second, part2(test))
}

/**
 * Pretty print a part's result
 */
private fun <T> partResult(part: Int, expected: T, actual: T) {
    "$part: expected ${expected}, got ${actual}. ${if (expected == actual) "✅" else "❌"}".println()
}