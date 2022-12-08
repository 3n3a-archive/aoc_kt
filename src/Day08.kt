enum class Side {
    LEFT, RIGHT
}

enum class Direction {
    TOP, BOTTOM
}

fun Day08() {
    fun preprocess(input: List<String>): Array<IntArray> {
        return input
            .map { it.toCharArray().map {
                it2 -> Integer.parseInt(it2.toString())
            }.toIntArray() }
            .toTypedArray()
    }

    fun isBiggerThanRowSide(side: Side, chosenNumberIndex: Int, allNumbers: IntArray): Boolean {
        val chosenNumber = allNumbers[chosenNumberIndex + 1]
        var newChosenNumberIndex = 0

        var allNumberSlice: IntArray = when (side) {
            Side.LEFT -> {
                newChosenNumberIndex = chosenNumberIndex
                allNumbers.sliceArray(0..chosenNumberIndex)
            }

            Side.RIGHT -> {
                newChosenNumberIndex = 0
                allNumbers.sliceArray(chosenNumberIndex + 2 until allNumbers.size)
            }
        }

        //println("side: ${side.name}, numbers: ${allNumberSlice.toList()}")

        var outputStates = mutableListOf<Boolean>()

        for ((index, number) in allNumberSlice.withIndex()) {
            if ((index != newChosenNumberIndex) and (chosenNumber > number)) {
                outputStates.add(true)
            } else if (chosenNumber <= number) {
                outputStates.add(false)
            }
        }

        outputStates.forEach {
            if (!it) return false
        }
        return true
    }

    fun isBiggerThanColumnDirection(direction: Direction, currentRowIndex: Int, chosenNumberRowIndex: Int, allTrees: Array<IntArray>): Boolean {
        val chosenNumber = allTrees[currentRowIndex + 1][chosenNumberRowIndex + 1]

        var newChosenNumberIndex = chosenNumberRowIndex + 1
        var newCurrentRowIndex = 0
        var allTreeSlice = when (direction) {
            Direction.TOP -> {
                newCurrentRowIndex = currentRowIndex + 1
                allTrees.slice(0..currentRowIndex)
            }

            Direction.BOTTOM -> {
                allTrees.slice(currentRowIndex + 2 until allTrees.size)
            }
        }

        val currentRow = allTrees[newCurrentRowIndex]

        print("direction: ${direction.name}, chosen: $chosenNumber, numbers: ")

        var outputStates = mutableListOf<Boolean>()
        for ((index, row) in allTreeSlice.withIndex()) {
            val numberAtSameIndex = row[newChosenNumberIndex]
            print("$numberAtSameIndex, ")
            /*if (!row.contentEquals(currentRow)) {
                (chosenNumber > numberAtSameIndex).also { outputStates.add(it) }
            }*/
            if ((index != newChosenNumberIndex) and (chosenNumber > numberAtSameIndex)) {
                outputStates.add(true)
            } else if (chosenNumber <= numberAtSameIndex) {
                outputStates.add(false)
            }
        }

        println(outputStates)

        outputStates.forEach {
            if (!it) return false
        }
        return true
    }

    fun getInnerTrees(allTrees: Array<IntArray>): Array<IntArray> {
        var innerTrees = mutableListOf<IntArray>()
        for ((index, row) in allTrees.withIndex()) {
            if (index > 0 && index < allTrees.size - 1) {
                var innerRowTrees = mutableListOf<Int>()
                for ((innerIndex, column) in row.withIndex()) {
                    if (innerIndex > 0 && innerIndex < (row.size - 1)) {
                        innerRowTrees.add(column)
                    }
                }
                innerTrees.add(innerRowTrees.toIntArray())
            }
        }
        return innerTrees
            .toTypedArray()
    }

    fun part1(input: List<String>): Int {
        val processed = preprocess(input)
        val innerTrees = getInnerTrees(processed)

        val outerVisible = processed.size * 4 - 4
        var innerVisible = 0

        for ((index, row) in innerTrees.withIndex()) {
            val originalRow = processed[index + 1]
            println("========= row: $index ==================")
            for ((indexInner, column) in row.withIndex()) {
                val isVisibleFromLeft = isBiggerThanRowSide(Side.LEFT, indexInner, originalRow)
                val isVisibleFromRight = isBiggerThanRowSide(Side.RIGHT, indexInner, originalRow)

                val isVisibleFromTop = isBiggerThanColumnDirection(Direction.TOP, index, indexInner, processed)
                val isVisibleFromBottom = isBiggerThanColumnDirection(Direction.BOTTOM, index, indexInner, processed)

                val isVisible = isVisibleFromLeft or isVisibleFromRight or isVisibleFromTop or isVisibleFromBottom

                println("position: [$index/$indexInner] = $column; isVisible $isVisible (l: $isVisibleFromLeft, r: $isVisibleFromRight, t: $isVisibleFromTop, b: $isVisibleFromBottom)")

                if (isVisible) innerVisible = innerVisible.inc()

            }
        }

        println("outside visible: $outerVisible")
        println("inner visible: $innerVisible")

        return innerVisible + outerVisible
    }

    fun part2(input: List<String>): Int {
        
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    //check(part2(testInput) == 4)

    val input = readInput("Day08")
    println("1: " + part1(input))
    //println("2: " + part2(input))
}

