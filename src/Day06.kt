enum class Direction {
    Up, Down, Left, Right
}

data class Position(val y: Int, val x: Int)

fun Direction.move(p: Position) = when (this) {
    Direction.Up -> Position(p.y - 1, p.x)
    Direction.Down -> Position(p.y + 1, p.x)
    Direction.Left -> Position(p.y, p.x - 1)
    Direction.Right -> Position(p.y, p.x + 1)
}

fun Direction.turnRight() = when (this) {
    Direction.Up -> Direction.Right
    Direction.Down -> Direction.Left
    Direction.Left -> Direction.Up
    Direction.Right -> Direction.Down
}

fun <T> List<List<T>>.inBounds(p: Position) = -1 < p.y && -1 < p.x && p.y < this.size && p.x < this[p.y].size

fun load(input: List<String>): Triple<Pair<Position, Direction>, List<MutableList<Char>>, List<MutableList<Int>>> {
    var pos: Position? = null
    var dir = Direction.Up
    val visited = mutableListOf<MutableList<Int>>()
    val map = input.mapIndexed { y, line ->
        val visitedRow = mutableListOf<Int>()
        visited.add(visitedRow)
        val l = mutableListOf<Char>()
        line.filter { it == '.' || it == '^' || it == '#' }.forEachIndexed { x, item ->
            if (item == '^') {
                pos = Position(y, x)
            }
            visitedRow.add(0)
            l.add(item)
        }
        l
    }
    if (pos == null) {
        throw IllegalArgumentException("Missing starting position!")
    }
    var p: Position = pos
    while (map.inBounds(p)) {
        visited[p.y][p.x]++
        var next = dir.move(p)
        while (map.inBounds(next) && map[next.y][next.x] == '#') {
            dir = dir.turnRight()
            next = dir.move(p)
        }
        p = next
    }
    return Triple(pos to Direction.Up, map, visited)
}

fun main() {
    fun part1(input: List<String>): Long {
        val (_, _, visited) = load(input)
        return visited.sumOf { row -> row.sumOf { if (it != 0) 1L else 0L } }
    }

    fun part2(input: List<String>): Long {
        val (start, map, visited) = load(input)
        val (startingPos, startingDir) = start
        var loops = 0L
        for (y in map.indices) {
            for (x in map[y].indices) {
                if (!(startingPos.y == y && startingPos.x == x) && visited[y][x] != 0) {
                    var p = startingPos.copy()
                    var dir = startingDir
                    val origin = map[y][x]
                    val turns = mutableListOf<Position>()
                    var loopPresent = false
                    map[y][x] = '#'
                    while (map.inBounds(p) && !loopPresent) {
                        var next = dir.move(p)
                        var turned = false
                        while (map.inBounds(next) && map[next.y][next.x] == '#') {
                            dir = dir.turnRight()
                            next = dir.move(p)
                            turned = true
                        }
                        if (turned) {
                            turns.add(p)
                            val occurrences =
                                turns.mapIndexedNotNull { i, t -> if (t.y == p.y && t.x == p.x) i else null }
                            if (occurrences.size == 3) {
                                var matches = 0
                                val dif = occurrences[1] - occurrences[0]
                                for (i in occurrences[0]..(occurrences[1])) {
                                    if (turns[i] == turns[i + dif]) {
                                        matches++
                                    }
                                }
                                if (matches == dif + 1) {
                                    loopPresent = true
                                    loops++
                                }
                            }
                        }
                        p = next
                    }
                    map[y][x] = origin
                }
            }
        }
        return loops
    }

    // Solutions
    val solutions = readInput("Day06_solutions").map { line ->
        val items = line.split("   ").map { it.toInt() }
        Pair(items[0].toLong(), items[1].toLong())
    }

    // Run
    result("Test 1", "Day06_test", solutions[0], ::part1, ::part2)
    result("Actual", "Day06", solutions[1], ::part1, ::part2)
}


