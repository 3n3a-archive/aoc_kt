import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * Splits the given `Iterable` into two at index [n].
 *
 * @return Pair<List<T>, List<T>>
 */
fun <T> Iterable<T>.splitAt(n: Int): Pair<List<T>, List<T>> =
    when {
        n < 0 ->
            throw IllegalArgumentException(
                "Requested split at index $n is less than zero.")

        n == 0 ->
            emptyList<T>() to toList()

        this is Collection<T> && (n >= size) ->
            toList() to emptyList()

        else -> {
            var idx = 0
            val dn = if (this is Collection<T>) size - n else n
            val left = ArrayList<T>(n)
            val right = ArrayList<T>(dn)

            @Suppress("UseWithIndex")
            for (item in this) {
                when (idx++ >= n) {
                    false -> left.add(item)
                    true -> right.add(item)
                }
            }
            left to right
        }
    }.let {
        it.first.optimizeReadOnlyList2() to it.second.optimizeReadOnlyList2()
    }


/**
 * Original function is internal in the stdlib
 */
fun <T> List<T>.optimizeReadOnlyList2(): List<T> = when (size) {
    0 -> emptyList()
    1 -> listOf(this[0])
    else -> this
}