fun Day03() {
    fun preprocess(input: List<String>): List<List<CharArray>> {
        val charArrays = input
            .map { it.toCharArray() }
            .map {
                val size = it.size
                listOf(
                    it.copyOfRange(0, (size + 1) / 2),
                    it.copyOfRange((size + 1) / 2, size)
                )
            }
        return charArrays
    }

    fun preprocess2(input: List<String>): List<List<CharArray>> {
        return input
            .chunked(3)
            .map { it.map { it2 -> it2.toCharArray() } }
    }

    fun getPriority(char: Char): Int {
        val charCode = char.code
        if (charCode in 97..122) {
            return charCode - 96
        } else if (charCode in 65..90) {
            return charCode - 38
        }
        return -1
    }

    fun part1(input: List<String>): Int {
        val processed = preprocess(input)
        var commonItems = mutableListOf<Char>()

        for (rucksack in processed) {
            val compartment1 = rucksack[0]
            val compartment2 = rucksack[1]

            for (letter in compartment1) {
                val isAtIndex = compartment2.indexOf(letter)
                if (isAtIndex != -1) {
                    commonItems.add(compartment2[isAtIndex])
                    break
                }
            }
        }

        val commonNumbers = commonItems.map { getPriority(it) }

        return commonNumbers.sum()
    }

    fun part2(input: List<String>): Int {
        val processed = preprocess2(input)
        var commonItems = mutableListOf<Char>()

        for (rucksackGroup in processed) {
            val rucksack1 = rucksackGroup[0]
            val rucksack2 = rucksackGroup[1]
            val rucksack3= rucksackGroup[2]

            for (letter in rucksack1) {
                if (rucksack2.contains(letter) && rucksack3.contains(letter)) {
                    commonItems.add(letter)
                    break
                }

            }
        }

        val commonNumbers = commonItems.map { getPriority(it) }

        return commonNumbers.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println("1: " + part1(input))
    println("2: " + part2(input))
}
