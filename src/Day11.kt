fun main() {
    fun processRound(monkeys: List<Monkey>, reduceWorryLevel: (Long) -> Long) {
        for (monkey in monkeys) {
            while (monkey.items.any()) {
                monkey.throughput += monkey.items.size
                for(item in monkey.items) {
                    val newWorryLevel = reduceWorryLevel(monkey.updateWorryLevel(item))
                    if (newWorryLevel % monkey.testValue == 0L) {
                        monkeys[monkey.monkeyIfTrue].items.add(newWorryLevel)
                    } else {
                        monkeys[monkey.monkeyIfFalse].items.add(newWorryLevel)
                    }
                }
                monkey.items.clear()
            }
        }
    }

    fun part1(input: List<String>): Int {
        val monkeys = input.chunked(7).map { Monkey.build(it) }
        repeat(20) {
            processRound(monkeys) { it / 3 }
        }

        return monkeys
            .map { it.throughput }
            .sortedDescending()
            .let { it[0] * it[1] }
    }

    fun part2(input: List<String>): Long {
        val monkeys = input.chunked(7).map { Monkey.build(it) }
        val reduceWorryLevel: Long = monkeys.map { m -> m.testValue.toLong() }.reduce(Long::times)

        repeat(10000) {
            processRound(monkeys) { item -> item % reduceWorryLevel }
//            if (it + 1 in setOf(1, 20, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000)) {
//                println("Round ${it + 1}: $monkeys")
//            }
        }

        return monkeys
            .map { it.throughput.toLong() }
            .sortedDescending()
            .let { it[0] * it[1] }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 10605)
    check(part2(testInput) == 2713310158)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}

class Monkey(
    val testValue: Int, val monkeyIfTrue: Int, val monkeyIfFalse: Int,
    startingItems: List<Long>, operation: List<String>
) {
    var throughput: Int = 0

    val items: MutableList<Long>
    private val modifyOperation: Operation
    private val modifyValue: String

    init {
        items = startingItems.toMutableList()
        modifyOperation = Operation.get(operation[0])
        modifyValue = operation[1]
    }

    override fun toString(): String {
        return "Throughput $throughput"
    }

    fun updateWorryLevel(item: Long): Long {
        return when (modifyOperation) {
            Operation.ADD -> item + modifyValue.toInt()
            Operation.MULTIPLY -> when (modifyValue) {
                "old" -> item * item
                else -> item * modifyValue.toInt()
            }
        }
    }

    companion object {
        fun build(input: List<String>): Monkey {
            val startingItems = input[1].removePrefix("  Starting items: ").split(", ").map { it.toLong() }
            val operation = input[2].split(" ").takeLast(2)
            val testValue = input[3].split(" ").last().toInt()
            val ifTrue = input[4].split(" ").last().toInt()
            val ifFalse = input[5].split(" ").last().toInt()
            return Monkey(testValue, ifTrue, ifFalse, startingItems, operation)
        }
    }
}

enum class Operation {
    ADD, MULTIPLY;

    companion object {
        fun get(op: String) = when (op) {
            "+" -> ADD
            "*" -> MULTIPLY
            else -> throw UnsupportedOperationException("Invalid operation")
        }
    }
}