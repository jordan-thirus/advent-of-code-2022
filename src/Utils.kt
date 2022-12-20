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

fun printVerbose(msg: String, isVerbose: Boolean) {
    if(isVerbose) println(msg)
}

data class Point(val y: Int, val x: Int){
    fun left(): Point = copy(x = x-1)
    fun right(): Point = copy(x = x + 1)
    fun up(): Point = copy(y = y - 1)
    fun down(): Point = copy(y = y + 1)

    fun neighbors() = setOf(left(), right(), up(), down())
    fun isInBounds(yRange: IntRange, xRange: IntRange) = y in yRange && x in xRange
}
