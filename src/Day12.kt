import java.util.PriorityQueue

fun main() {
    val startChar = 'S'
    val endChar = 'E'

    fun getHeight(height: Char) = when(height){
        endChar -> 'z'
        startChar -> 'a'
        else -> height
    }

    fun shortestPathToEnd(priorityQueue: PriorityQueue<Step>, input: List<String>): Int {
        val visited = mutableSetOf<Point>()

        while (priorityQueue.any()) {
            val step = priorityQueue.remove()

            //if we've been here, don't process again
            if (!visited.contains(step.point)) {
                //if it's the end, return
                if (step.height == endChar) {
                    return step.distanceTraveled
                }
                //mark as visited
                visited.add(step.point)
                val nextDistance = step.distanceTraveled + 1
                for (n in step.neighbors().filter { it.isInBounds(input.indices, input[0].indices) }) {
                    val nextHeight = input[n.y][n.x]
                    if (getHeight(step.height) - getHeight(nextHeight) >= -1) {
                        priorityQueue.add(Step(n, nextHeight, nextDistance))
                    }
                }
            }
        }

        return Int.MAX_VALUE
    }

    fun part1(input: List<String>): Int {
        val priorityQueue = PriorityQueue<Step>()

        input.forEachIndexed { index, chars ->
            if (chars.contains(startChar)){
                priorityQueue.add(Step.build(index, chars.indexOf(startChar), startChar, 0))
            }
        }

        return shortestPathToEnd(priorityQueue, input)
    }

    fun part2(input: List<String>): Int {
        val priorityQueue = PriorityQueue<Step>()
        input.forEachIndexed { index, chars ->
            chars.forEachIndexed { i, c ->
                if(getHeight(c) == 'a'){
                    priorityQueue.add(Step.build(index, i, c, 0))
                }
            }
        }
        return shortestPathToEnd(priorityQueue, input)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 31)
    check(part2(testInput) == 29)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))

}

data class Step(val point: Point, val height: Char, val distanceTraveled: Int): Comparable<Step> {
    fun neighbors(): Set<Point> = point.neighbors()

    companion object {
        fun build(y: Int, x: Int, height: Char, distanceTraveled: Int): Step =
            Step(Point(y, x), height, distanceTraveled)
    }

    override fun compareTo(other: Step): Int {
        return distanceTraveled - other.distanceTraveled
    }
}
