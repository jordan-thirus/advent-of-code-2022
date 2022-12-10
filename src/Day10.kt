fun main() {
    fun processCommand(command: String, registerX: Int, processCycle: () -> Unit): Int {
        return when (command) {
            "noop" -> {
                processCycle()
                registerX
            }

            else -> { //addx
                val v = command.split(" ")[1].toInt()
                processCycle()
                processCycle()
                registerX + v
            }
        }
    }

    fun part1(input: List<String>): Int {
        var registerX = 1
        var cycle = 1
        var signalStrength = 0

        input.forEach {
            registerX = processCommand(it, registerX) {
                val calculateStrength = (cycle-20)%40 == 0
                if(calculateStrength){
                    signalStrength += registerX * cycle
                }
                cycle++
            }
        }

        return signalStrength
    }

    fun part2(input: List<String>) {
        var registerX = 1
        var position = 0
        val crtWidth = 40
        val crt = mutableListOf<Char>()

        input.forEach { line ->
            registerX = processCommand(line, registerX) {
                if (position in registerX - 1..registerX + 1) {
                    crt.add('#')
                } else {
                    crt.add('.')
                }
                position++
                if (position == crtWidth) {
                    position = 0
                }
            }
        }

        crt.chunked(crtWidth)
            .map { it.joinToString(separator = "") }
            .forEach { println(it) }

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140)
    //part2(testInput)

    val input = readInput("Day10")
    println(part1(input))
    part2(input)
}
