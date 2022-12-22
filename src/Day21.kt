fun main() {
    fun buildMonkey(inp: String, input: List<String>): MonkeyEval {
        val split = inp.split(" ")
        val name = split[0].substringBefore(":")
        return if (split.size == 2) {
            MonkeyEval(name, Operation.RETURN, value = split[1].toLong())
        } else {
            val monkey1Line = input.find { it.startsWith(split[1]) }!!
            val monkey2Line = input.find { it.startsWith(split[3]) }!!
            MonkeyEval(
                name,
                Operation.get(split[2]),
                monkey1 = buildMonkey(monkey1Line, input),
                monkey2 = buildMonkey(monkey2Line, input)
            )
        }
    }

    fun part1(input: List<String>): Long {
        val root = buildMonkey(input.find { it.startsWith("root") }!!, input)
        return root.yell()
    }

    fun part2(input: List<String>): Long {
        val root = buildMonkey(input.find { it.startsWith("root") }!!, input)
        var treeContainingHuman: MonkeyEval
        var value: Long

        if(root.monkey1!!.containsHuman()){
            treeContainingHuman = root.monkey1
            value = root.monkey2!!.yell()
        } else {
            treeContainingHuman = root.monkey2!!
            value = root.monkey1.yell()
        }

        while(!treeContainingHuman.isHuman){
            val monkey1 = treeContainingHuman.monkey1!!
            val monkey2 = treeContainingHuman.monkey2!!
            if(monkey1.containsHuman()){
                val temp = monkey2.yell()
                when(treeContainingHuman.operation){
                    Operation.ADD -> value -= temp
                    Operation.SUBTRACT -> value += temp
                    Operation.MULTIPLY -> value /= temp
                    Operation.DIVIDE -> value *= temp
                    else -> throw Exception("Invalid Operation")
                }
                treeContainingHuman = monkey1
            } else {
                val temp = monkey1.yell()
                when(treeContainingHuman.operation){
                    Operation.ADD -> value -= temp
                    Operation.SUBTRACT -> value = temp - value //value = temp - monkey2.yell(); value - temp = - monkey2.yell(); temp - value = monkey2.yell()
                    Operation.MULTIPLY -> value /= temp
                    Operation.DIVIDE -> value = temp / value //value = temp / monkey2.yell(); value * monkey2.yell() = temp; monkey2.yell = temp / value
                    else -> throw Exception("Invalid Operation")
                }
                treeContainingHuman = monkey2
            }
        }

        treeContainingHuman.value = value
        //validate
        check(root.monkey1.yell() == root.monkey2.yell())

        return value
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day21_test")
    check(part1(testInput) == 152L)
    check(part2(testInput) == 301L)

    val input = readInput("Day21")
    println(part1(input))
    println(part2(input))
}

data class MonkeyEval(
    val name: String, val operation: Operation, var value: Long? = null,
    val monkey1: MonkeyEval? = null, val monkey2: MonkeyEval? = null
) {
    fun yell(): Long {
        return when (operation) {
            Operation.RETURN -> value!!
            Operation.ADD -> monkey1!!.yell() + monkey2!!.yell()
            Operation.SUBTRACT -> monkey1!!.yell() - monkey2!!.yell()
            Operation.MULTIPLY -> monkey1!!.yell() * monkey2!!.yell()
            Operation.DIVIDE -> monkey1!!.yell() / monkey2!!.yell()
        }
    }

    val isHuman: Boolean get() = name == "humn"

    fun containsHuman(): Boolean {
        return if (isHuman) true
        else if (value != null) false //there are no children to check
        else monkey1!!.containsHuman() || monkey2!!.containsHuman() //check children
    }
}
