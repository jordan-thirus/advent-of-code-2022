import kotlin.math.sign

fun main() {
    fun moveToNewPosition(prev: Point, point: Point): Point {
        return if (!prev.isAdjacent(point)) {
            val x = (prev.x - point.x).sign + point.x
            val y = (prev.y - point.y).sign + point.y
            Point(x, y)
        } else {
            point
        }
    }

    fun part1(input: List<String>): Int {
        val head = Point(0, 0)
        var tail = Point(0, 0)
        val visited = mutableSetOf(tail)

        for (line in input) {
            val move = line.split(" ")
            val distance = move[1].toInt()
            val direction = move[0]

            repeat(distance) {
                head.move(direction)
                tail = moveToNewPosition(head, tail)
                visited.add(tail)
            }
        }
        return visited.size
    }

    fun part2(input: List<String>): Int {
        var snake = Array(10) { Point(0,0)}.toList()
        val visited = mutableSetOf(snake.last())


        for (line in input) {
            val move = line.split(" ")
            val distance = move[1].toInt()
            val direction = move[0]

            repeat(distance) {
                snake.first().move(direction)
                snake = snake.runningReduce { acc, point -> moveToNewPosition(acc, point) }
                visited.add(snake.last())
            }
        }
        return visited.size
    }

    // test if implementation meets criteria from the description, like:
    check(part1(readInput("Day09_test1")) == 13)
    check(part2(readInput("Day09_test2")) == 36)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}

data class Point(var x: Int, var y: Int) {
    fun isAdjacent(other: Point): Boolean =
        x - other.x in -1..1 && y - other.y in -1..1

    fun move(direction: String) {
        when (direction) {
            "L" -> x--
            "R" -> x++
            "U" -> y++
            "D" -> y--
            else -> throw IllegalArgumentException("Bad move")
        }
    }
}