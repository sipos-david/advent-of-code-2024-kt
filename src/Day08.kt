fun main() {
  fun load(input: List<String>): Pair<List<List<Char>>, Map<Char, Set<Position>>> {
    val antennas = mutableMapOf<Char, MutableSet<Position>>()
    val map = input.mapIndexed { y, line ->
      line.mapIndexed { x, item ->
        if (item != '.') {
          val positions = antennas[item]
          if (positions == null) {
            antennas[item] = mutableSetOf(Position(y, x))
          } else {
            positions.add(Position(y, x))
          }
        }
        item
      }
    }
    return map to antennas
  }

  fun part1(input: List<String>): Long {
    val (map, antennas) = load(input)
    val antinodes = mutableSetOf<Position>()
    for ((node, positions) in antennas.entries) {
      for (p in positions) {
        for (pair in positions) {
          if (p != pair) {
            val antinode = Position(2 * pair.y - p.y, 2 * pair.x - p.x)
            if (map.inBounds(antinode) && map[antinode.y][antinode.x] != node) {
              antinodes.add(antinode)
            }
          }
        }
      }
    }
    return antinodes.size.toLong()
  }

  fun part2(input: List<String>): Long {
    val (map, antennas) = load(input)
    val antinodes = mutableSetOf<Position>()
    for (positions in antennas.values) {
      for (p in positions) {
        for (pair in positions) {
          antinodes.add(pair)
          if (p != pair) {
            var i = 1
            var antinode = Position(p.y + i * (pair.y - p.y), p.x + i * (pair.x - p.x))
            while (map.inBounds(antinode)) {
              antinodes.add(antinode)
              i++
              antinode = Position(p.y + i * (pair.y - p.y), p.x + i * (pair.x - p.x))
            }
          }
        }
      }
    }
    return antinodes.size.toLong()
  }

  // Solutions
  val solutions = readInput("Day08_solutions").map { line ->
    val items = line.split("   ").map { it.toLong() }
    Pair(items[0].toLong(), items[1].toLong())
  }

  // Run
  result("Test 1", "Day08_test", solutions[0], ::part1, ::part2)
  result("Actual", "Day08", solutions[1], ::part1, ::part2)
}
