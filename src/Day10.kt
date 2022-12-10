fun Day10() {
    var sumOfSignalStrengths = 0

    fun preprocess(input: List<String>): List<List<String>> {
        return input
            .map { it.split(" ") }
    }

    fun doCycleInc(cyclesCounter: Int, cyclesRecalc: Int, registerX: Int): List<Int> {
        var cyclesCounter = cyclesCounter.inc()
        var cyclesRecalc = cyclesRecalc.inc()

        // cycle values
        //println("\t\t\t\t cycles: $cyclesCounter")
        if ((cyclesCounter == 20 ) or ((cyclesRecalc == 40) and (cyclesCounter <= 220))) {
            cyclesRecalc = 0

            val signalStrength = cyclesCounter * registerX
            sumOfSignalStrengths += signalStrength
            println("===== signal strength: $signalStrength")
        }

        return listOf(cyclesCounter, cyclesRecalc)
    }

    fun part1(input: List<String>): Int {
        // reset
        sumOfSignalStrengths = 0

        val instructions = preprocess(input)
        var registerX = 1 // register
        var cyclesCounter = 0
        var cyclesRecalc = 0
        for (currentInstruction in instructions) {

            val instruction = currentInstruction[0]
            when (instruction) {
                "noop" -> {
                    val cycleResult = doCycleInc(cyclesCounter, cyclesRecalc, registerX)
                    cyclesCounter = cycleResult[0]
                    cyclesRecalc = cycleResult[1]
                    // does nothing
                }
                "addx" -> {
                    val amount = Integer.parseInt(currentInstruction[1])

                    for (i in 1..2) {
                        val cycleResult = doCycleInc(cyclesCounter, cyclesRecalc, registerX)
                        cyclesCounter = cycleResult[0]
                        cyclesRecalc = cycleResult[1]
                    }

                    // adds amount to register
                    registerX += amount
                }
            }
            //println("$currentInstruction \t\t X: $registerX; cycles: $cyclesCounter")
        }
        println("Sum of all signal strengths: $sumOfSignalStrengths")
        return sumOfSignalStrengths
    }

    fun part2(input: List<String>): Int {
        
        return 2
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140)
    //check(part2(testInput) == 4)

    val input = readInput("Day10")
    println("\n############### Part 1 ################")
    println("1: " + (part1(input) == 14220))
    //println("2: " + part2(input))
}

