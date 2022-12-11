import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val head = Point(0,0)
        var tail = Point(0,0)

        val visited = mutableSetOf(tail)

        fun changePosition(distance: Int, move: (Point) -> Unit){
            repeat(distance){
                val lastPosition = head.copy()
                move(head)
                if(!head.isAdjacent(tail)){
                    if(head.isNotInSameRowOrColumn(tail)){
                        //move diagonally to the position of the last segment
                        tail = lastPosition.copy()

                    }else {
                        move(tail)
                    }
                    visited.add(tail)
                }
            }
        }

        for(line in input){
            val move = line.split(" ")
            when(move[0]){
                "L" -> changePosition(move[1].toInt()) { it.x-- }
                "R" -> changePosition(move[1].toInt()) { it.x++ }
                "U" -> changePosition(move[1].toInt()) { it.y++ }
                "D" -> changePosition(move[1].toInt()) { it.y-- }
                else -> throw IllegalArgumentException("Bad move")
            }
            //println("head: $head, tail: $tail")
        }
        return visited.size
    }

    fun part2(input: List<String>): Int {
        var snake = listOf(Point(0,0),Point(0,0),Point(0,0),Point(0,0),Point(0,0),Point(0,0),Point(0,0),Point(0,0),Point(0,0),Point(0,0))
        val visited = mutableSetOf(snake.last())

        fun changePosition(distance: Int, move: (Point) -> Unit){
            repeat(distance){
                //println("Moved head from $lastPosition to ${snake.first()}")
                move(snake.first())
                snake = snake.runningReduce { acc, point ->
                    var newPosition = point.copy()
                    if (!acc.isAdjacent(point)) {
                            if(point.x > acc.x){
                                newPosition.x--
                            } else if (point.x < acc.x) {
                                newPosition.x++
                            }
                            if(point.y > acc.y){
                                newPosition.y--
                            } else if (point.y < acc.y){
                                newPosition.y++
                            }
                            if(!acc.isAdjacent(newPosition)){
                                println("Point $acc and point $newPosition are not adjacent and should be")
                            }
                        }

                    newPosition
                }
                //println(snake)
                visited.add(snake.last())
            }
            println(visited.size)
        }

        for(line in input){
            println(line)
            val move = line.split(" ")
            when(move[0]){
                "L" -> changePosition(move[1].toInt()) { it.x-- }
                "R" -> changePosition(move[1].toInt()) { it.x++ }
                "U" -> changePosition(move[1].toInt()) { it.y++ }
                "D" -> changePosition(move[1].toInt()) { it.y-- }
                else -> throw IllegalArgumentException("Bad move")
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

data class Point(var x: Int, var y: Int){
    fun isAdjacent(other: Point): Boolean {
        val adj = x - other.x in -1..1 && y - other.y in -1..1
        return adj
    }
    fun isNotInSameRowOrColumn(other: Point): Boolean =
        x != other.x && y != other.y
}