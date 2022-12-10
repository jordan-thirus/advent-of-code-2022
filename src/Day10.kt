fun main() {
    fun part1(input: List<String>): Int {
        var registerX = 1
        val cycles = mutableListOf<Int>()

        fun processCycle() = cycles.add(registerX)

        input.forEach { line ->
            when(line){
                "noop" -> {
                    processCycle()
                }
                else -> { //addx
                    val v = line.split(" ")[1].toInt()
                    processCycle()
                    processCycle()
                    registerX += v
                }
            }
        }
        val strength20 = cycles[19] * 20
        val strength60 = cycles[59] * 60
        val strength100 = cycles[99] * 100
        val strength140 = cycles[139] * 140
        val strength180 = cycles[179] * 180
        val strength220 = cycles[219] * 220
        return strength20 + strength60 + strength100 + strength140 + strength180 + strength220
    }

    fun part2(input: List<String>) {
        var registerX = 1
        var position = 0
        val crt = mutableListOf<Char>()

        fun processCycle() {
            if(position in registerX-1..registerX+1){
                crt.add('#')
            }
            else {
                crt.add('.')
            }
            position++
            if(position == 40){
                position = 0
            }
        }

        input.forEach { line ->
            when(line){
                "noop" -> {
                    processCycle()
                }
                else -> { //addx
                    val v = line.split(" ")[1].toInt()
                    processCycle()
                    processCycle()
                    registerX += v
                }
            }
        }
        crt.chunked(40)
            .map { it.joinToString(separator = "") }
            .forEach{ println(it) }

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140)
    //part2(testInput)

    val input = readInput("Day10")
    println(part1(input))
    part2(input)
}
