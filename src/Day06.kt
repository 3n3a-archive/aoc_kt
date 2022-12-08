fun Day06() {

    fun getXUniqueLetters(input: String, uniqueAmount: Int): Int {
        val inputAsChars = input.toCharArray()
        val processedChars = mutableListOf<Char>()

        for ((index, char) in inputAsChars.withIndex()) {
            processedChars.add(char)

            if (index >= uniqueAmount) {
                val lastXLetters = processedChars.takeLast(uniqueAmount)
                if (lastXLetters.distinct().size == lastXLetters.size) {
                    return index + 1
                }
            }
        }

        return -1
    }

    fun part1(input: String): Int {
        return getXUniqueLetters(input, 4)
    }

    fun part2(input: String): Int {
        return getXUniqueLetters(input, 14)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputToText("Day06_test")
    check(part1(testInput) == 10)
    check(part2(testInput) == 29)

    val input = readInputToText("Day06")
    println("1: " + (part1(input) == 1034))
    println("2: " + (part2(input) == 2472))
}

