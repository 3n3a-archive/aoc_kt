fun Day04() {
    fun preprocess(input: List<String>) {
        return input
    }

    fun part1(input: List<String>): Int {
        val processed = preprocess(input)

        return input.size
    }

    fun part2(input: List<String>): Int {
        val processed = preprocess(input)

        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 1)

    val input = readInput("Day04")
    println("1: " + part1(input))
    println("2: " + part2(input))
}

