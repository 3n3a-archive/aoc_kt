fun Day01() {
    fun preprocess(input: List<String>): List<Int> {
        return input 
          .joinToString("|")
          .split("||")
          .map { it.split("|").map { it.toInt() }.sum() }
    }

    fun part1(input: List<String>): Int {
        val processedInput = preprocess(input)
        return processedInput.maxOrNull()?: 0
    }
    
    fun part2(input: List<String>): Int {
        val processedInput = preprocess(input)
        return processedInput
          .sortedDescending()
          .slice(IntRange(0, 2))
          .sum()
    }

    // test if implementation meets criteria from the description
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)
    
    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}