import kotlin.math.absoluteValue

fun main() {
    fun isSequenceOk(list: List<Long>): Boolean {
        var ok = 0
        val isDescending = list[0] > list[1]
        for (index in list.indices) {
            if (index != list.size - 1) {
                val distance = if (isDescending) {
                    list[index] - list[index + 1]
                } else {
                    list[index + 1] - list[index]
                }
                if ((distance < 1 || 3 < distance)) {
                    break
                }
            }
            ok = index
        }
        return ok == list.size - 1
    }

    fun part1(input: List<String>): Long {
        return input.map { it.split(" ").map { it.toLong() } }.sumOf {
            return@sumOf if (isSequenceOk(it)) 1L else 0L
        }
    }

    fun part2(input: List<String>): Long {
        return input.map { it.split(" ").map { it.toLong() } }.sumOf {
            var ok = isSequenceOk(it)
            if (!ok) {
                for (index in it.indices) {
                    val list = it.toMutableList()
                    list.removeAt(index)
                    ok = isSequenceOk(list)
                    if (ok) {
                        break
                    }
                }
            }
            if (ok) 1L else 0L
        }
    }

    // Solutions
    val solutions = readInput("Day02_solutions").map { line ->
        val items = line.split("   ").map { it.toInt() }
        Pair(items[0].toLong(), items[1].toLong())
    }

    // Run
    result("Test", "Day02_test", solutions[0], ::part1, ::part2)
    result("Actual", "Day02", solutions[1], ::part1, ::part2)
}
