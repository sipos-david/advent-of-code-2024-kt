typealias Operation = (acc: Long, next: Long) -> Long

fun multiplication(acc: Long, next: Long) = acc * next

fun addition(acc: Long, next: Long) = acc + next

fun concatenation(acc: Long, next: Long) = (acc.toString() + next.toString()).toLong()

fun main() {
  fun load(input: List<String>): List<Pair<Long, List<Long>>> {
    return input.mapNotNull { line ->
      val l = line.split(": ")
      if (l.size != 2) {
        return@mapNotNull null
      }
      l[0].toLong() to l[1].split(" ").mapNotNull { it.toLongOrNull() }
    }
  }

  fun calculate(result: Long, list: List<Long>, acc: Long, idx: Int, ops: List<Operation>, op: Operation): Boolean {
    if (idx == list.size) {
      return acc == result
    }
    val nextAcc = op(acc, list[idx])
    for (nextOp in ops) {
      if (calculate(result, list, nextAcc, idx + 1, ops, nextOp)) {
        return true
      }
    }
    return false
  }

  fun part1(input: List<String>): Long {
    val list = load(input)
    return list.filter { (result, variables) ->
      calculate(result, variables, variables[0], 1, listOf(::multiplication, ::addition), ::multiplication) ||
              calculate(result, variables, variables[0], 1, listOf(::multiplication, ::addition), ::addition)
    }.sumOf { it.first }
  }

  fun part2(input: List<String>): Long {
    val list = load(input)
    return list.filter { (result, variables) ->
      calculate(
        result,
        variables,
        variables[0],
        1,
        listOf(::multiplication, ::addition, ::concatenation),
        ::multiplication
      ) ||
              calculate(
                result,
                variables,
                variables[0],
                1,
                listOf(::multiplication, ::addition, ::concatenation),
                ::addition
              ) ||
              calculate(
                result,
                variables,
                variables[0],
                1,
                listOf(::multiplication, ::addition, ::concatenation),
                ::concatenation
              )
    }.sumOf { it.first }
  }

  // Solutions
  val solutions = readInput("Day07_solutions").map { line ->
    val items = line.split("   ").map { it.toLong() }
    Pair(items[0].toLong(), items[1].toLong())
  }

  // Run
  result("Test 1", "Day07_test", solutions[0], ::part1, ::part2)
  result("Actual", "Day07", solutions[1], ::part1, ::part2)
}
