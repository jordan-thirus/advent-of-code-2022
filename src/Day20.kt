import kotlin.math.abs

fun main() {
    fun mix(lst: MutableList<Node>, verbose: Boolean) {
        for (index in lst.indices) {
            val i = lst.indexOfFirst { it.mixOrder == index }
            val node = lst[i]

            val moveAmount = node.value
            if (abs(moveAmount) > lst.size) moveAmount % lst.size
            if (moveAmount != 0L) {
                lst.removeAt(i)
                var newIndex = i + moveAmount
                if (newIndex > lst.lastIndex) {
                    newIndex %= lst.size
                } else if (newIndex <= 0) {
                    newIndex = newIndex % lst.size + lst.size
                }

                lst.add(newIndex.toInt(), node)
            }

            printVerbose(lst.toString(), verbose)
        }
    }

    fun part1(input: List<String>, verbose: Boolean): Long {
        val lst = input.mapIndexed { index, it -> Node(it.toLong(), index) }.toMutableList()

        mix(lst, verbose)

        val indexOfZero = lst.indexOfFirst { it.value == 0L }
        val indexOfZeroPlus1000 = (indexOfZero + 1000) % lst.size
        val indexOfZeroPlus2000 = (indexOfZero + 2000) % lst.size
        val indexOfZeroPlus3000 = (indexOfZero + 3000) % lst.size

        return lst[indexOfZeroPlus1000].value + lst[indexOfZeroPlus2000].value + lst[indexOfZeroPlus3000].value
    }

    fun part2(input: List<String>, verbose: Boolean): Long {
        val lst = input.mapIndexed { index, it -> Node(it.toLong() * 811589153, index) }.toMutableList()

        repeat(10){
            mix(lst, false)
            printVerbose(lst.toString(), verbose)
        }

        val indexOfZero = lst.indexOfFirst { it.value == 0L }
        val indexOfZeroPlus1000 = (indexOfZero + 1000) % lst.size
        val indexOfZeroPlus2000 = (indexOfZero + 2000) % lst.size
        val indexOfZeroPlus3000 = (indexOfZero + 3000) % lst.size

        return lst[indexOfZeroPlus1000].value + lst[indexOfZeroPlus2000].value + lst[indexOfZeroPlus3000].value
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day20_test")
    check(part1(testInput, true) == 3L)
    check(part2(testInput, true) == 1623178306L)

    val input = readInput("Day20")
    println(part1(input, false))
    println(part2(input, false))
}

data class Node(val value: Long, val mixOrder: Int) {
    override fun toString() = value.toString()
}