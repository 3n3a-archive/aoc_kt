fun Day10() {
    var sumOfSignalStrengths = 0
    var crtScreen: MutableList<MutableList<String>> = mutableListOf(mutableListOf(""))
    var crtPosition: MutableList<Int> = mutableListOf(0, 0)

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

    fun printImage() {
        println("=========== IMAGE ===========")
        for (line in crtScreen){
            println(line.joinToString(""))
        }
        println("=========== END ===========")
    }

    fun doCycleInc2(cyclesCounter: Int, cyclesRecalc: Int, registerX: Int): List<Int> {
        var cyclesCounter = cyclesCounter.inc()
        var cyclesRecalc = cyclesRecalc.inc()

        // cycle values
        //println("\t\t\t\t cycles: $cyclesCounter")
        if ((cyclesCounter == 20) or ((cyclesRecalc == 40) and (cyclesCounter <= 220))) {
            cyclesRecalc = 0

            val signalStrength = cyclesCounter * registerX
            sumOfSignalStrengths += signalStrength
            println("===== signal strength: $signalStrength")
        }



        // draw pixel at position
        val spritePositions = (registerX - 1)..(registerX + 1)
        crtScreen[crtPosition[0]].add(crtPosition[1], if (spritePositions.contains(crtPosition[1])) "#" else ".")

        //println("x: ${crtPosition[0]} y: ${crtPosition[1]}\tsprite: ${spritePositions} -> screen ${crtPosition[1]}")

        // new pixel
        crtPosition[1] = crtPosition[1].inc()

        if ((cyclesCounter >= 40) and (cyclesCounter % 40 == 0)) {


            // print old line
            //println(crtScreen[crtPosition[0]].joinToString(""))

            // new line on screen
            crtPosition[0] = crtPosition[0].inc()
            crtPosition[1] = 0
            crtScreen.add(mutableListOf(""))
        }



        return listOf(cyclesCounter, cyclesRecalc)
    }

    fun part2(input: List<String>): String {
        // reset
        sumOfSignalStrengths = 0
        crtScreen = mutableListOf(mutableListOf(""))
        crtPosition = mutableListOf(0, 0)

        val instructions = preprocess(input)
        var registerX = 1 // register
        var cyclesCounter = 0
        var cyclesRecalc = 0
        for (currentInstruction in instructions) {

            val instruction = currentInstruction[0]
            when (instruction) {
                "noop" -> {
                    val cycleResult = doCycleInc2(cyclesCounter, cyclesRecalc, registerX)
                    cyclesCounter = cycleResult[0]
                    cyclesRecalc = cycleResult[1]
                    // does nothing
                }
                "addx" -> {
                    val amount = Integer.parseInt(currentInstruction[1])

                    for (i in 1..2) {
                        val cycleResult = doCycleInc2(cyclesCounter, cyclesRecalc, registerX)
                        cyclesCounter = cycleResult[0]
                        cyclesRecalc = cycleResult[1]
                    }

                    // adds amount to register
                    registerX += amount
                }
            }
            //println("$currentInstruction \t\t X: $registerX; cycles: $cyclesCounter")
        }

        printImage()

        return ""
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    //check(part1(testInput) == 13140)
    check(part2(testInput) == "")

    val input = readInput("Day10")
    println("\n############### Part 1 ################")
    //println("1: " + (part1(input) == 14220))

    println("\n############### Part 2 ################")
    println("2: " + part2(input))
}

