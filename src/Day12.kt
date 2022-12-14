import java.util.PriorityQueue

fun main() {
    val startChar = 'S'
    val endChar = 'E'

    fun getHeight(height: Char) = when(height){
        endChar -> 'z'
        startChar -> 'a'
        else -> height
    }

    fun part1(input: List<String>): Int {
        val priorityQueue = PriorityQueue<Step>()

        input.forEachIndexed { index, chars ->
            if (chars.contains(startChar)){
                priorityQueue.add(Step(index, chars.indexOf(startChar), startChar, 0))
            }
        }

        fun pointIsInBounds(point: Pair<Int, Int>) = point.first in input.indices && point.second in input[0].indices

        val visited = mutableSetOf<Pair<Int, Int>>()

        while (priorityQueue.any()){
            val step = priorityQueue.remove()

            //if we've been here, don't process again
            if(!visited.contains(step.point)){
                //if it's the end, return
                if(step.height == endChar){
                    return step.distanceTraveled
                }
                //mark as visited
                visited.add(step.point)
                val nextDistance = step.distanceTraveled + 1
                for(n in step.neighbors){
                    if(pointIsInBounds(n)){
                        val nextHeight = input[n.first][n.second]
                        if(getHeight(step.height) - getHeight(nextHeight) >= -1){
                            priorityQueue.add(Step.build(n, nextHeight, nextDistance))
                        }
                    }
                }
            }
        }

        return Int.MAX_VALUE
    }

    fun part2(input: List<String>): Int {val priorityQueue = PriorityQueue<Step>()

        input.forEachIndexed { index, chars ->
            chars.forEachIndexed { i, c ->
                if(getHeight(c) == 'a'){
                    priorityQueue.add(Step(index, i, c, 0))
                }
            }
        }

        fun pointIsInBounds(point: Pair<Int, Int>) = point.first in input.indices && point.second in input[0].indices

        val visited = mutableSetOf<Pair<Int, Int>>()

        while (priorityQueue.any()){
            val step = priorityQueue.remove()

            //if we've been here, don't process again
            if(!visited.contains(step.point)){
                //if it's the end, return
                if(step.height == endChar){
                    return step.distanceTraveled
                }
                //mark as visited
                visited.add(step.point)
                val nextDistance = step.distanceTraveled + 1
                for(n in step.neighbors){
                    if(pointIsInBounds(n)){
                        val nextHeight = input[n.first][n.second]
                        if(getHeight(step.height) - getHeight(nextHeight) >= -1){
                            priorityQueue.add(Step.build(n, nextHeight, nextDistance))
                        }
                    }
                }
            }
        }

        return Int.MAX_VALUE
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 31)
    check(part2(testInput) == 29)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))

}

data class Step(val y: Int, val x: Int, val height: Char, val distanceTraveled: Int) : Comparable<Step> {
    val point: Pair<Int, Int>
        get() = Pair(y, x)

    val neighbors: Set<Pair<Int, Int>>
        get() = setOf(Pair(y-1, x), Pair(y+1,x), Pair(y, x-1), Pair(y, x+1))

    companion object {
        fun build(point: Pair<Int, Int>, height: Char, distanceTraveled: Int = 0): Step =
            Step(point.first, point.second, height, distanceTraveled)
    }

    override fun compareTo(other: Step): Int {
        return distanceTraveled - other.distanceTraveled
    }
}
