import kotlinx.serialization.json.*

fun main() {
    fun compare(left: JsonElement, right: JsonElement): Int {
        val isLeftInt = left is JsonPrimitive
        val isRightInt = right is JsonPrimitive

        if(isLeftInt && isRightInt){
            return left.jsonPrimitive.int.compareTo(right.jsonPrimitive.int)
        } else if( !isLeftInt && !isRightInt ){
            for(i in left.jsonArray.indices){
                if(i !in right.jsonArray.indices){
                    return 1
                }
                else {
                    val result = compare(left.jsonArray[i], right.jsonArray[i])
                    if(result != 0) return result
                }
            }
            if(left.jsonArray.size == right.jsonArray.size){
                return 0
            }
            return -1
        } else if (isRightInt){
            return compare(left.jsonArray, buildJsonArray { add(right) })
        }
        else {
            return compare(buildJsonArray { add(left) }, right.jsonArray)
        }
    }

    fun part1(input: List<String>): Int {
        return input.chunked(3)
            .mapIndexed{ index, strings -> Holder(index+1, Json.parseToJsonElement(strings[0]), Json.parseToJsonElement(strings[1])) }
            .filter { compare(it.left, it.right) == -1}
            .sumOf { it.index }
    }

    fun part2(input: List<String>): Int {
        val decoderKeys = setOf(Json.parseToJsonElement("[[2]]"), Json.parseToJsonElement("[[6]]"))
        return input.filter { it != "" }
            .map { Json.parseToJsonElement(it) }
            .plus(decoderKeys)
            .sortedWith { left, right -> compare(left, right) }
            .let { (it.indexOf(decoderKeys.first()) + 1) * (it.indexOf(decoderKeys.last()) + 1) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 140)

    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}

data class Holder(val index: Int, val left: JsonElement, val right: JsonElement)


