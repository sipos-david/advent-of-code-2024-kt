import kotlin.math.floor

fun main() {
    fun load(input: List<String>): Pair<Map<Long, List<Long>>, List<List<Long>>> {
        var loadRules = true
        var rules = mutableMapOf<Long, MutableList<Long>>()
        var pages = mutableListOf<List<Long>>()
        for (line in input) {
            if (line.isBlank()) {
                loadRules = false
            } else {
                if (loadRules) {
                    val l = line.split("|").map { it.toLong() }
                    if (l.size == 2) {
                        val item = rules[l[0]]
                        if (item != null) {
                            item.add(l[1])
                        } else {
                            rules[l[0]] = mutableListOf(l[1])
                        }
                    }
                } else {
                    pages.add(line.split(",").map { it.toLong() })
                }
            }
        }
        return rules to pages
    }

    fun getFirstBadIndicesInOrderingOrNull(page: List<Long>, rules: Map<Long, List<Long>>): Pair<Int, Int>? {
        for (index in page.indices) {
            val rule = rules[page[index]]
            if (rule != null && index > 0) {
                var i = index
                while (-1 < i) {
                    if (rule.contains(page[i])) {
                        return index to i
                    }
                    i--
                }
            }
        }
        return null
    }

    fun part1(input: List<String>): Long {
        val (rules, pages) = load(input)
        return pages.sumOf {
            if (getFirstBadIndicesInOrderingOrNull(it, rules) == null) {
                it[(floor(it.size / 2.0)).toInt()]
            } else {
                0L
            }
        }
    }

    fun part2(input: List<String>): Long {
        val (rules, pages) = load(input)
        return pages.sumOf {
            var swap = getFirstBadIndicesInOrderingOrNull(it, rules)
            if (swap != null) {
                val list = it.toMutableList()
                while (swap != null) {
                    val temp = list[swap.first]
                    list[swap.first] = list[swap.second]
                    list[swap.second] = temp
                    swap = getFirstBadIndicesInOrderingOrNull(list, rules)
                }
                list[(floor(it.size / 2.0)).toInt()]
            } else {
                0L
            }
        }
    }

    // Solutions
    val solutions = readInput("Day05_solutions").map { line ->
        val items = line.split("   ").map { it.toInt() }
        Pair(items[0].toLong(), items[1].toLong())
    }

    // Run
    result("Test 1", "Day05_test", solutions[0], ::part1, ::part2)
    result("Actual", "Day05", solutions[1], ::part1, ::part2)

}
