fun main() {

    fun parseTrees(input: List<String>): List<List<Tree>> {
        return input.map { row ->
            row.map { tree ->
                Tree(tree.digitToInt())
            }
        }
    }

    fun isTreeVisibleFromDirection(trees: List<List<Tree>>, rangeX: IntProgression, rangeY: IntProgression) {
        for (x in rangeX) {
            var maxHeight = 0
            for (y in rangeY) {
                //detect if tree is on an edge
                if (x == rangeX.first || y == rangeY.first
                    || trees[y][x].height > maxHeight
                ) {
                    trees[y][x].visible = true
                    maxHeight = trees[y][x].height
                }
            }
        }
        for (y in rangeY) {
            var maxHeight = 0
            for (x in rangeX) {
                //detect if tree is on an edge
                if (x == rangeX.first || y == rangeY.first
                    || trees[y][x].height > maxHeight
                ) {
                    trees[y][x].visible = true
                    maxHeight = trees[y][x].height
                }
            }
        }
    }

    fun part1(input: List<String>): Int {
        val trees = parseTrees(input)
        val yRange = trees.indices
        val xRange = trees[0].indices
        //west / north
        isTreeVisibleFromDirection(trees, xRange, yRange)
        //east / south
        isTreeVisibleFromDirection(trees, xRange.reversed(), yRange.reversed())

        return trees.sumOf { row ->
            row.count { tree -> tree.visible }
        }
    }

    fun getViewInDirection(treeHeight: Int, treesPast: List<Int>): Int{
        val v = treesPast.indexOfFirst { h -> h >= treeHeight }
        if(v == -1) return treesPast.size + 1
        return v + 1
    }

    fun part2(input: List<String>): Int {
        val trees = parseTrees(input)
        val westEdge = trees[0].lastIndex
        val southEdge = trees.lastIndex
        //west
        for (x in trees[0].indices) {
            for (y in trees.indices) {
                when (x) {
                    0 -> trees[y][x].viewWest = 0
                    1 -> trees[y][x].viewWest = 1
                    else -> {
                        val heights = trees[y].subList(1, x).map { tree -> tree.height }.reversed()
                        trees[y][x].viewWest = getViewInDirection(trees[y][x].height, heights)
                    }
                }
            }
        }
        //east
        for (x in trees[0].indices) {
            for (y in trees.indices) {
                when (x) {
                    westEdge -> trees[y][x].viewEast = 0
                    westEdge-1 -> trees[y][x].viewEast = 1
                    else -> {
                        val heights = trees[y].subList(x + 1, westEdge).map { tree -> tree.height }
                        trees[y][x].viewEast =
                            getViewInDirection(trees[y][x].height,  heights)
                    }
                }
            }
        }
        //north
        for (x in trees[0].indices) {
            for (y in trees.indices) {
                when (y) {
                    0 -> trees[y][x].viewNorth = 0
                    1 -> trees[y][x].viewNorth = 1
                    else -> {
                        val heights = trees.subList(1, y).map { row -> row[x] }.map { tree -> tree.height }.reversed()
                        trees[y][x].viewNorth = getViewInDirection(trees[y][x].height, heights)
                    }
                }
            }
        }
        //south
        for (x in trees[0].indices) {
            for (y in trees.indices) {
                when (y) {
                    southEdge -> trees[y][x].viewSouth = 0
                    southEdge-1 -> trees[y][x].viewSouth = 1
                    else -> {
                        val heights = trees.subList(y + 1, southEdge).map { row -> row[x] }.map { tree -> tree.height }
                        trees[y][x].viewSouth =
                            getViewInDirection(trees[y][x].height, heights)
                    }
                }
            }
        }

//        trees.forEach { row ->
//            row.forEach { tree -> print("${tree.scenicScore}\t") }
//            println()
//        }
        return trees.maxOf { row -> row.maxOf { tree -> tree.scenicScore } }
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}

data class Tree(
    val height: Int, var visible: Boolean = false,
    var viewWest: Int = 1, var viewEast: Int = 1,
    var viewNorth: Int = 1, var viewSouth: Int = 1
) {
    val scenicScore: Int
        get() = viewWest * viewEast * viewNorth * viewSouth
}
