import kotlin.math.absoluteValue

fun main() {
    fun read(input: List<String>): Pair<IntArray, IntArray> {
        val groupLeft = IntArray(input.size)
        val groupRight = IntArray(input.size)

        input.forEachIndexed { index, line ->
            val items = line.split("   ").map { it.toInt() }
            if (items.size != 2) {
                throw IllegalArgumentException()
            }
            groupLeft[index] = items[0]
            groupRight[index] = items[1]
        }

        return Pair(groupLeft, groupRight)
    }


    fun part1(input: List<String>): Long {
        val (groupLeft, groupRight) = read(input)

        groupLeft.sort()
        groupRight.sort()

        var distance = 0L

        groupLeft.forEachIndexed { index, item ->
            distance += (item - groupRight[index]).absoluteValue
        }

        return distance
    }

    fun part2(input: List<String>): Long {
        val (groupLeft, groupRight) = read(input)

        val occurrences = mutableMapOf<Int, Int>()

        groupRight.forEach { occurrences[it] = 0 }

        groupLeft.forEach {
            if (occurrences.containsKey(it)) {
                occurrences[it] = occurrences[it]!! + 1
            }
        }

        return groupRight.sumOf {
            val occurrence = occurrences[it]
            if (occurrence != null) {
                (occurrence * it).toLong()
            } else {
                0L
            }
        }
    }

    // Solutions
    val solutions = readInput("Day01_solutions").map { line ->
        val items = line.split("   ").map { it.toInt() }
        Pair(items[0].toLong(), items[1].toLong())
    }

    // Run
    result("Test", "Day01_test", solutions[0], ::part1, ::part2)
    result("Actual", "Day01", solutions[1], ::part1, ::part2)
}
