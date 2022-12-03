fun Day02() {
    fun getMappedChar( map: Map<Char, Char>, input: Char): Char? {
        return map.get(input)
    }

    fun getPointsForPlayer2( winner: Int, chosenItem: Char?  ): Int {
        var points = 0
        when(winner) {
            0 -> points += 3
            1 -> points += 0
            2 -> points += 6 
        }

        when(chosenItem) {
            'R' -> points += 1
            'P' -> points += 2
            'S' -> points += 3
        }

        return points
    }

    fun rockPaperScissor(player1: Char, player2: Char): Int {
        // return 1 --> player 1 wins
        // return 2 --> player 2 wins
        // return 0 --> draw

        // HAndle exception of Scissor beats Paper
        if (player1 == 'S' && player2 == 'P') {
            return 1
        } else if (player2 == 'S' && player1 == 'P') {
            return 2
        }
        
        val player1GameResults = player1.compareTo(player2)

        when(player1GameResults) {
            1 -> return 2
            0 -> return 0
            -1 -> return 1
            else -> println("todo")
        }
        return 99
    }

    fun preprocess(input: List<String>): List<List<Char?>> {
        val replacementMap = mapOf('A' to 'R', 'B' to 'P', 'C' to 'S', 'X' to 'R', 'Y' to 'P', 'Z' to 'S',)
        return input
          .map { 
            it.split(" ").map { 
              getMappedChar(replacementMap, it.toCharArray()[0] ) 
            }
          }
    }

    fun part1(input: List<String>): Int {
        val processed = preprocess(input)

        var sumOfPoints = 0
        for (element in processed)  {
            val gameStats = rockPaperScissor(element[0] ?: 'E', element[1] ?: 'E')
            val points = getPointsForPlayer2(gameStats, element[1])
            sumOfPoints += points 
        }
        return sumOfPoints
    }

    fun getWinningMove(char: Char?): Char {
        when(char) {
            'R' -> return 'P'
            'P' -> return 'S'
            'S' -> return 'R'
        }
        return 'E'
    }

    fun getLosingMove(char: Char?): Char {
        when(char) {
            'R' -> return 'S'
            'P' -> return 'R'
            'S' -> return 'P'
        }
        return 'E'
    }

    fun getDesiredOutcome(player1: Char?, outcome: Char?): Char? {
        val x = 'R'
        val y = 'P'
        val z = 'S'
        when(outcome) {
            x -> return getLosingMove(player1)
            y -> return player1 // draw
            z -> return getWinningMove(player1)
        }
        return 'E'
    }
    
    fun part2(input: List<String>): Int {
        val processed = preprocess(input)

        var sumOfPoints = 0
        for (element in processed)  {
            val player2 = getDesiredOutcome(element[0], element[1])
            val gameStats = rockPaperScissor(element[0] ?: 'E', player2 ?: 'E')
            val points = getPointsForPlayer2(gameStats, player2)
            sumOfPoints += points 
        }
        return sumOfPoints
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day02_test")
    // check(part1(testInput) == 15)
    
    val input = readInput("Day02")
    println("1: " + part1(input))
    println("2: " + part2(input))
}

