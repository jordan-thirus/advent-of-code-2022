fun main() {
    fun getItemPriority(item: Char): Int{
        val priority = if(item.isLowerCase()) {
            item.minus('a') + 1
        } else {
            item.minus('A') + 27
        }
        return priority
    }

    fun part1(input: List<String>): Int {
        fun findItemToRearrange(items: String): Char{
            val midPoint = items.length / 2
            return items.toCharArray(0, midPoint).intersect(items.toCharArray(midPoint, items.length).toSet())
                 .single()
        }

        return input.map { line -> findItemToRearrange(line) }
            .sumOf { item -> getItemPriority(item) }
    }

    fun part2(input: List<String>): Int {
        return input.chunked(3)
            .map { elves -> elves.map {rucksack -> rucksack.toSet() }
                .reduce{ acc, rucksack -> acc.intersect(rucksack) }
                .single()
            }
            .sumOf { badge -> getItemPriority(badge) }
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}