fun main() {

    fun getPairs(line: String) : List<Pair<Int, Int>> {
        val assignments = line.split(',','-')
        return listOf(Pair(assignments[0].toInt(), assignments[1].toInt()),
            Pair(assignments[2].toInt(), assignments[3].toInt()))
            .sortedBy { pair -> pair.first }
    }

    fun containedSchedule(elf1: Pair<Int, Int>,  elf2: Pair<Int, Int>) : Boolean {
        return (elf1.first in elf2.first..elf2.second &&
                elf1.second in elf2.first..elf2.second) ||
                (elf2.first in elf1.first..elf1.second &&
                        elf2.second in elf1.first..elf1.second)
    }

    fun overlapSchedule(elf1: Pair<Int, Int>,  elf2: Pair<Int, Int>) : Boolean {
        return elf2.first <= elf1.second
    }

    fun part1(input: List<String>): Int {
        return input.map { line -> getPairs(line) }
            .count { pair -> containedSchedule(pair[0], pair[1]) }
    }

    fun part2(input: List<String>): Int {
        return input.map { line -> getPairs(line) }
            .count { pair -> overlapSchedule(pair[0], pair[1]) }
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}