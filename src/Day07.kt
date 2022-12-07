fun main() {
    fun parse(input: List<String>): Directory {
        val que : ArrayDeque<Directory> = ArrayDeque()
        val root = Directory("/")
        que.addLast(root)
        for(line in input.subList(1, input.size)){
            val instructions = line.split(' ')
            when(instructions[0]){
                "$" -> {
                    when(instructions[1]){
                        "cd" -> {
                            //println("changing to ${instructions[2]}")
                            when(instructions[2]){
                                ".." -> que.removeLast()
                                else -> que.addLast(que.last().getSubdirectory(instructions[2]))
                            }
                        }
                        "ls" -> {
                            //println("next will be directory list")
                            continue
                        }
                    }
                }
                "dir" -> {
                    //println("adding directory ${instructions[1]}")
                    que.last().addContent(Directory(instructions[1]))
                }
                else -> {
                    //println("adding file ${instructions[1]}")
                    que.last().addContent(File(instructions[1], instructions[0].toInt()))
                }
            }

        }
        print(root.buildDirectoryStructure())
        return root
    }

    fun getTotalSizeOfDirsUnderSize(root: Directory, size: Int): Int {
        var totalSize = 0
        val que : ArrayDeque<Directory> = ArrayDeque()
        que.add(root)
        while(!que.isEmpty()){
            val dir = que.removeFirst()
            if(dir.size <= size) totalSize += dir.size
            que.addAll(dir.getSubdirectories())
        }

        return totalSize
    }

    fun part1(input: List<String>): Int {
        val root = parse(input)
        return getTotalSizeOfDirsUnderSize(root, 100000)
    }

    fun getSmallestDirOverSize(root: Directory, size: Int): Directory {
        var smallestDir = root
        val que : ArrayDeque<Directory> = ArrayDeque()
        que.add(root)
        while(!que.isEmpty()){
            val dir = que.removeFirst()
            if(dir.size in size..smallestDir.size) {
                smallestDir = dir
            }
            if(dir.size > size){
                que.addAll(dir.getSubdirectories())
            }
        }

        return smallestDir
    }

    fun part2(input: List<String>): Int {
        val root = parse(input)
        val requiredSpace = 30000000
        val totalSpace = 70000000
        val neededSpace = root.size - (totalSpace - requiredSpace)
        val dir = getSmallestDirOverSize(root, neededSpace)
        println("Smallest directory is $dir")
        return dir.size
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

interface Content {
    val type: ContentType
    val name: String
    val size: Int
}

class File(override val name: String, override val size: Int): Content {
    override val type: ContentType = ContentType.FILE

    override fun toString(): String {
        return "- $name (file, size=$size)\n"
    }
}

class Directory(override val name: String): Content {
    override val type: ContentType = ContentType.DIRECTORY

    private val contents: MutableList<Content> = mutableListOf()
    override val size: Int
        get() = contents.sumOf { c -> c.size }

    fun addContent(content: Content) = contents.add(content)
    fun getSubdirectory(name: String): Directory
        = getSubdirectories().first() { c -> c.name == name }
    fun getSubdirectories(): List<Directory> = contents.filterIsInstance<Directory>()


    fun buildDirectoryStructure(prefix: String = ""): StringBuilder {
        val sb = java.lang.StringBuilder()
        val newPrefix = prefix + "\t"
        sb.append("$prefix$this")
        contents.forEach{c ->
            when(c.type){
                ContentType.FILE -> sb.append("$newPrefix$c")
                ContentType.DIRECTORY -> sb.append((c as Directory).buildDirectoryStructure(newPrefix))
            }
        }
        return sb
    }

    override fun toString(): String {
        return "- $name (dir)\n"
    }
}

enum class ContentType {
    FILE, DIRECTORY
}