fun main() {

    fun buildCrateStacks(
        input: List<String>,
        instructionSplitIndex: Int
    ): MutableList<MutableList<Char>> {
        val crateStacks: MutableList<MutableList<Char>> = mutableListOf()
        for (line in input.subList(0, instructionSplitIndex - 1)) {
            //println(line)
            var lineIndex = 1
            var stackIndex = 0
            while (lineIndex < line.length) {
                if (crateStacks.size < stackIndex + 1) {
                    crateStacks.add(mutableListOf())
                }
                val crate = line[lineIndex]
                if (crate != ' ') {
                    crateStacks[stackIndex].add(0, crate)
                }

                lineIndex += 4
                stackIndex += 1
            }

        }
        return crateStacks
    }

    fun part1(input: List<String>): String {
        val instructionSplitIndex = input.indexOf("")
        val crateStacks: MutableList<MutableList<Char>> = buildCrateStacks(input, instructionSplitIndex)

        //run instructions
        for (line in input.subList(instructionSplitIndex + 1, input.size)) {
            //println(line)
            val instructions = line.split(" ")

            val count = instructions[1].toInt()
            val from = instructions[3].toInt() - 1
            val to = instructions[5].toInt() - 1
            crateStacks[to].addAll(crateStacks[from].takeLast(count).reversed())
            crateStacks[from] = crateStacks[from].subList(0, crateStacks[from].size - count)
            //println(crateStacks)
        }

        return crateStacks.map { s -> s.lastOrNull() ?: "" }
            .joinToString("")
    }

    fun part2(input: List<String>): String {
        val instructionSplitIndex = input.indexOf("")
        val crateStacks: MutableList<MutableList<Char>> = buildCrateStacks(input, instructionSplitIndex)

        //run instructions
        for (line in input.subList(instructionSplitIndex + 1, input.size)) {
            //println(line)
            val instructions = line.split(" ")
            val count = instructions[1].toInt()
            val from = instructions[3].toInt() - 1
            val to = instructions[5].toInt() - 1

            crateStacks[to].addAll(crateStacks[from].takeLast(count))
            crateStacks[from] = crateStacks[from].subList(0, crateStacks[from].size - count)
            //println(crateStacks)
        }

        return crateStacks.map { s -> s.lastOrNull() ?: "" }
            .joinToString("")
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}