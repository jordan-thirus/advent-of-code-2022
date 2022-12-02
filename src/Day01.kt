fun main() {
    fun getElves(input: List<String>): List<Int> {
        val elves = mutableListOf(0)
        var index = 0
        input.forEach { line ->
            when (line) {
                "" -> {
                    elves.add(0)
                    index++
                }

                else -> {
                    elves[index] += (line.toInt())
                }
            }
        }
        return elves
    }

    fun part1(input: List<String>): Int {
        val elves = getElves(input)
        return elves.max()

    }

    fun part2(input: List<String>): Int {
        val elves = getElves(input)
        return elves
            .sortedDescending()
            .take(3)
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
