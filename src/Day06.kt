fun main() {
    fun findStartOf(input: String, size: Int): Int{
        for(i in input.indices){
            val start = i + size
            val marker = input.substring(i, start)
            if(marker.toSet().size == size){
                return start
            }
        }
        return 0 //should never get here
    }

    fun part1(input: String): Int = findStartOf(input, 4)
    fun part2(input: String): Int = findStartOf(input, 14)

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput.first()) == 7)
    check(part2(testInput.first()) == 19)

    val input = readInput("Day06")
    println(part1(input.first()))
    println(part2(input.first()))
}