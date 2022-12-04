fun Day04() {
    fun preprocess(input: List<String>): List<List<Set<Int>>> {
        return input
            .map {
                it.split(",")
            } // to pair of elves
            .map {
                it.map {
                        it2 -> it2.split("-")
                }
            } // each elf gets pair of numbers
            .map {
                it.map {
                        it2 -> IntRange(
                            it2[0].toInt(),
                            it2[1].toInt()
                        ).toSet()
                }
            } // each elf gets range of numbers
    }

    fun part1(input: List<String>): Int {
        val processed = preprocess(input)
        var fullyContainedSets = 0
        for ((elfOne, elfTwo) in processed) {
            if (elfOne.containsAll(elfTwo) ||
                elfTwo.containsAll(elfOne)) {
                fullyContainedSets = fullyContainedSets.inc()
            }
        }
        return fullyContainedSets
    }

    fun part2(input: List<String>): Int {
        val processed = preprocess(input)
        var intersectedSets = 0
        for ((elfOne, elfTwo) in processed) {
            if (elfOne.intersect(elfTwo).isNotEmpty() ||
                elfTwo.intersect(elfOne).isNotEmpty()) {
                intersectedSets = intersectedSets.inc()
            }
        }
        return intersectedSets
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println("1: " + part1(input))
    println("2: " + part2(input))
}

