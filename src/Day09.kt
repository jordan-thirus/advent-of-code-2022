import kotlin.math.sign

fun main() {

    fun Point.isAdjacent(other: Point): Boolean =
        x - other.x in -1..1 && y - other.y in -1..1

    fun Point.move(direction: String): Point =  when (direction) {
        "L" -> left()
        "R" -> right()
        "U" -> up()
        "D" -> down()
        else -> throw IllegalArgumentException("Bad move")
    }

    fun moveToNewPosition(prev: Point, point: Point): Point {
        return if (!prev.isAdjacent(point)) {
            val x = (prev.x - point.x).sign + point.x
            val y = (prev.y - point.y).sign + point.y
            Point(y, x)
        } else {
            point
        }
    }

    fun part1(input: List<String>): Int {
        var head = Point(0, 0)
        var tail = Point(0, 0)
        val visited = mutableSetOf(tail)

        for (line in input) {
            val move = line.split(" ")
            val distance = move[1].toInt()
            val direction = move[0]

            repeat(distance) {
                head = head.move(direction)
                tail = moveToNewPosition(head, tail)
                visited.add(tail)
            }
        }
        return visited.size
    }

    fun part2(input: List<String>): Int {
        var snake = Array(10) { Point(0,0)}.toList().toMutableList()
        val visited = mutableSetOf(snake.last())


        for (line in input) {
            val move = line.split(" ")
            val distance = move[1].toInt()
            val direction = move[0]

            repeat(distance) {
                snake[0] = snake[0].move(direction)
                snake = snake.runningReduce { acc, point -> moveToNewPosition(acc, point) }.toMutableList()
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

