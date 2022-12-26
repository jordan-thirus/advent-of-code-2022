import kotlin.math.pow

fun main() {
    fun String.toLong(verbose: Boolean): Long {
        var num = 0L
        this.reversed().forEachIndexed{ index, c ->
            val value = 5.0.pow(index).toLong()
            when(c) {
                '=' -> num -= value * 2
                '-' -> num -= value
                else -> num += value * c.digitToInt()
            }
        }

        printVerbose("SNAFU: $this, Decimal: $num", verbose)
        return num
    }

    fun Long.toSnafu(verbose: Boolean): String{
        val encode = "012=-"
        var working = this
        var snafu = ""
        while(working != 0L){
            val rem = (working % 5).toInt()
            snafu = encode[rem] + snafu
            working = (working + when(rem){
                4 -> 1
                3 -> 2
                else -> 0
            }) / 5
        }
        printVerbose("Decimal: $this, SNAFU: $snafu", verbose)

        return snafu
    }

    fun part1(input: List<String>, verbose: Boolean) =
        input.sumOf { it.toLong(verbose) }.toSnafu(verbose)


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day25_test")
    check(part1(testInput, false) == "2=-1=0")

    val input = readInput("Day25")
    println(part1(input, false))
}
