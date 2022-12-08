
data class Instruction(val amount: Int, val from: Int, val to: Int)
data class Day05PreprocessResult(val containers: MutableList<MutableList<String>>, val instructions: List<Instruction>)

fun Day05() {
    fun preprocess(input: String): Day05PreprocessResult {
        val containersRegex = "\\[(.)\\]".toRegex()
        val instructionsRegex = "move.(.*).from.(.*).to.(.*)".toRegex()

        val (containers, instructions) = input.split("\n\n")

        // get storage containers
        val matchedContainers = containers
            .split("\n")
            .dropLast(1)
            .map {
                containersRegex
                    .findAll(it)
                    .map {
                        it2 ->
                        val pos = it2.groups[0]!!.range
                        val value = it2.groupValues[0][1].toString()
                        mapOf<IntRange, String>(pos to value)
                    }
                    .toMutableList()
            }
            .flatten()
            .toMutableList()
            .groupBy(keySelector = { it.keys }, valueTransform = { it.values })
            .map { Pair(it.key.toList()[0], it.value) }
            .sortedBy { it.first.toList().sum() }
            .map {
                it.second.reversed()
            }
            .map { 
                it.map {
                    it2 -> it2.toList()[0]
                }
                    .toMutableList()
            }
            .toMutableList()

        //println(matchedContainers)

        // get instructions
        val matchedInstructions = instructions
            .split("\n")
            .map {
                instructionsRegex
                    .findAll(it)
                    .map { it2 ->
                        val amount = Integer.parseInt(it2.groupValues[1])
                        val from = Integer.parseInt(it2.groupValues[2])
                        val to = Integer.parseInt(it2.groupValues[3])
                        val instruction = Instruction(amount, from, to)
                        instruction
                    }
                    .toList()[0]
            }
            .toList()

        /*for (instruction in matchedInstructions) {
            val (amount, from, to) = instruction()
            println("$amount $from $to")
        }*/

        return Day05PreprocessResult(matchedContainers, matchedInstructions)
    }

    fun getTopMostContainers(containers: MutableList<MutableList<String>>): List<String> {
        return containers
            .map {
                it.last()
            }
            .toList()
    }

    fun part1(input: String): String {
        var (containers, instructions) = preprocess(input)

        //println(containers)

        for (instruction in instructions) {
            val (amount, from, to) = instruction

            val containersToMove = containers[from - 1].takeLast(amount).reversed()
            containers[from - 1] = containers[from - 1].dropLast(amount).toMutableList()

            containers[to - 1].addAll(containersToMove)

            //println("move $amount from $from to $to")
            //println(containers)
        }

        //println(containers)

        return getTopMostContainers(containers).joinToString("")
    }

    fun part2(input: String): String {
        var (containers, instructions) = preprocess(input)

        //println(containers)

        for (instruction in instructions) {
            val (amount, from, to) = instruction

            val containersToMove = containers[from - 1].takeLast(amount)
            containers[from - 1] = containers[from - 1].dropLast(amount).toMutableList()

            containers[to - 1].addAll(containersToMove)

            //println("move $amount from $from to $to")
            //println(containers)
        }

        //println(containers)

        return getTopMostContainers(containers).joinToString("")
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputToText("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInputToText("Day05")
    println("1: " + (part1(input) == "QNHWJVJZW"))
    println("2: " + (part2(input) == "BPCZJLFJW"))
}

