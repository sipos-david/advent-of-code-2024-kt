fun main() {
    fun part1(input: List<String>): Long {
        val mtx = input.map { line -> line.split("") }

        var occurrences = 0L

        for (y in mtx.indices) {
            for (x in mtx[y].indices) {
                if (x + 3 < mtx[y].size) {
                    if (mtx[y][x] == "X" && mtx[y][x + 1] == "M" && mtx[y][x + 2] == "A" && mtx[y][x + 3] == "S" ||
                        mtx[y][x] == "S" && mtx[y][x + 1] == "A" && mtx[y][x + 2] == "M" && mtx[y][x + 3] == "X"
                    ) {
                        occurrences++
                    }
                }
                if (y + 3 < mtx.size) {
                    if (
                        mtx[y][x] == "X" && mtx[y + 1][x] == "M" && mtx[y + 2][x] == "A" && mtx[y + 3][x] == "S" ||
                        mtx[y][x] == "S" && mtx[y + 1][x] == "A" && mtx[y + 2][x] == "M" && mtx[y + 3][x] == "X"
                    ) {
                        occurrences++
                    }
                }
                if (y + 3 < mtx.size && x + 3 < mtx[y].size) {
                    if (mtx[y][x] == "X" && mtx[y + 1][x + 1] == "M" && mtx[y + 2][x + 2] == "A" && mtx[y + 3][x + 3] == "S" ||
                        mtx[y][x] == "S" && mtx[y + 1][x + 1] == "A" && mtx[y + 2][x + 2] == "M" && mtx[y + 3][x + 3] == "X"
                    ) {
                        occurrences++
                    }
                }
                if (y + 3 < mtx.size && 0 <= x - 3) {
                    if (mtx[y][x] == "X" && mtx[y + 1][x - 1] == "M" && mtx[y + 2][x - 2] == "A" && mtx[y + 3][x - 3] == "S" ||
                        mtx[y][x] == "S" && mtx[y + 1][x - 1] == "A" && mtx[y + 2][x - 2] == "M" && mtx[y + 3][x - 3] == "X"
                    ) {
                        occurrences++
                    }
                }
            }
        }
        return occurrences
    }

    fun part2(input: List<String>): Long {
        val mtx = input.map { line -> line.split("") }

        var occurrences = 0L

        for (y in mtx.indices) {
            for (x in mtx[y].indices) {
                if (y + 2 < mtx.size && x + 2 < mtx[y].size) {
                    if (mtx[y][x] == "M" && mtx[y + 1][x + 1] == "A" && mtx[y + 2][x + 2] == "S" ||
                        mtx[y][x] == "S" && mtx[y + 1][x + 1] == "A" && mtx[y + 2][x + 2] == "M"
                    ) {
                        if (mtx[y][x+2] == "M" && mtx[y + 1][x + 1] == "A" && mtx[y + 2][x] == "S" ||
                            mtx[y][x+2] == "S" && mtx[y + 1][x + 1] == "A" && mtx[y + 2][x] == "M"
                        ) {
                            occurrences++
                        }
                    }
                }
            }
        }
        return occurrences
    }

    // Solutions
    val solutions = readInput("Day04_solutions").map { line ->
        val items = line.split("   ").map { it.toInt() }
        Pair(items[0].toLong(), items[1].toLong())
    }

    // Run
    result("Test 1", "Day04_test", solutions[0], ::part1, ::part2)
    result("Actual", "Day04", solutions[1], ::part1, ::part2)
}
