fun main() {
    fun part1(input: List<String>): Long {
        val extract = """mul\(\d+,\d+\)""".toRegex()
        return input.sumOf {
            extract.findAll(it).sumOf {
                val numbers =
                    it.value.replace("mul", "").replace("(", "").replace(")", "").split(",").map { it.toLong() }
                return@sumOf if (numbers.size == 2) {
                    numbers[0] * numbers[1]
                } else {
                    0
                }
            }
        }
    }

    fun part2(input: List<String>): Long {
        val extract = """(mul\(\d+,\d+\))|(don't\(\))|(do\(\))""".toRegex()
        var skip = false
        return input.sumOf {
            extract.findAll(it).sumOf {
                if (it.value == "don't()") {
                    skip = true
                    return@sumOf 0L
                }
                if (it.value == "do()") {
                    skip = false
                    return@sumOf 0L
                }
                if (skip == false) {
                    val numbers =
                        it.value.replace("mul", "").replace("(", "").replace(")", "").split(",").map { it.toLong() }
                    return@sumOf if (numbers.size == 2) {
                        numbers[0] * numbers[1]
                    } else {
                        0L
                    }
                } else {
                    return@sumOf 0L
                }
            }
        }
    }

    // Solutions
    val solutions = readInput("Day03_solutions").map { line ->
        val items = line.split("   ").map { it.toInt() }
        Pair(items[0].toLong(), items[1].toLong())
    }

    // Run
    result("Test 1", "Day03_test_1", solutions[0], ::part1, ::part2)
    result("Test 2", "Day03_test_2", solutions[1], ::part1, ::part2)
    result("Actual", "Day03", solutions[2], ::part1, ::part2)
}
