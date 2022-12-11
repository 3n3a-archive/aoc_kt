import java.math.BigInteger

data class Monkey(
    val index: Int,
    var startingItems: MutableList<BigInteger>,
    val operation: String,
    val testDivisibleBy: BigInteger,
    val ifTrue: Int,
    val ifFalse: Int,
    var inspectedItemsCount: BigInteger,
)
fun Day11() {
    var divideByThree = true

    fun preprocess(input: String): MutableList<Monkey> {
        val monkeyRegex = "Monkey.(.*):\\n.*Starting.items:.(.*)\\n.*Operation:.new.=.(.*)\\n.*Test:.divisible.by.(.*)\\n.*If.true:.throw.to.monkey.(.*)\\n.*If.false:.throw.to.monkey.(.*)".toRegex(RegexOption.MULTILINE)

        val results = monkeyRegex.findAll(input)

        val monkeys: MutableList<Monkey> = mutableListOf()

        for (result in results) {
            val currentMatchGroups = result.groups.toList().drop(1)

            val monkeyIndex = Integer.parseInt(currentMatchGroups[0]!!.value)
            val monkeyStartItems = currentMatchGroups[1]!!.value.split(", ").map { Integer.parseInt(it).toBigInteger() }
            val monkeyOperation = currentMatchGroups[2]!!.value
            val monkeyTest = Integer.parseInt(currentMatchGroups[3]!!.value).toBigInteger()
            val monkeyTestTrue = Integer.parseInt(currentMatchGroups[4]!!.value)
            val monkeyTestFalse = Integer.parseInt(currentMatchGroups[5]!!.value)

            monkeys.add(Monkey(
                monkeyIndex,
                monkeyStartItems.toMutableList(),
                monkeyOperation,
                monkeyTest,
                monkeyTestTrue,
                monkeyTestFalse,
                BigInteger("0")
            ))
        }

        return monkeys
    }

    fun doCalculation(first: BigInteger, second: BigInteger, operation: String): BigInteger {
        when(operation) {
            "+" -> {
                return first.add(second)
            }
            "*" -> {
                return first.multiply(second)
            }
        }
        return BigInteger("0")
    }

    fun inspectStartingItems(items: MutableList<BigInteger>, operation: String): List<BigInteger> {
        val newList = mutableListOf<BigInteger>()

        // inspect each item
        for (item in items) {
            // apply operation to item
            val (firstNumber, calcOperation, secondNumber) = operation.replace("old", item.toString()).split(" ")
            val calcResult: BigInteger = doCalculation(firstNumber.toBigInteger(), secondNumber.toBigInteger(), calcOperation)

            // divide by 3
            var dividedByThree: BigInteger = if (divideByThree) {
                calcResult.divide(BigInteger("3"))
            } else {
                calcResult
            }

            //println("Calc: $firstNumber $calcOperation $secondNumber = $calcResult")

            newList.add(dividedByThree)
        }
        return newList
    }

    fun executeTurn(monkey: Monkey, monkeys: MutableList<Monkey>): MutableList<Monkey> {
        // if no items return
        if (monkey.startingItems.size == 0) {
            return monkeys
        }

        val newItems = inspectStartingItems(monkey.startingItems, monkey.operation)
        monkeys.filter { it.index == monkey.index }[0].inspectedItemsCount = monkeys.filter { it.index == monkey.index }[0].inspectedItemsCount.add(newItems.size.toBigInteger())

        // reset starting items for current monkey
        monkeys.filter { it.index == monkey.index }[0].startingItems = mutableListOf()

        // test each item
        for (item in newItems) {
            // test item
            // is divisible without remainder
            val testResult = item.mod(monkey.testDivisibleBy) == 0.toBigInteger()

            // throw based on test result
            var monkeyToThrowTo = when (testResult) {
                true -> {
                    monkey.ifTrue
                }

                false -> {
                    monkey.ifFalse
                }
            }
            monkeys.filter { it.index == monkeyToThrowTo }[0].startingItems.add(item)
        }

        return monkeys
    }

    fun doRounds(monkeysInput: MutableList<Monkey>, rounds: Int): BigInteger {
        var monkeys = monkeysInput

        for (i in 1..rounds) {
            println("\n=== ROUND $i ===")
            for (monkey in monkeys) {
                monkeys = executeTurn(monkey, monkeys)
            }
        }

        monkeys.sortBy { it.inspectedItemsCount }
        monkeys.reverse()

        val topMonkeys = monkeys.take(2)
        var levelOfMonkeyBiz: BigInteger = topMonkeys[0].inspectedItemsCount.multiply(topMonkeys[1].inspectedItemsCount)

        println(monkeys)
        println("Level of Monkey Biz: $levelOfMonkeyBiz")
        return levelOfMonkeyBiz
    }

    fun part1(input: String): BigInteger {
        divideByThree = true
        val monkeys = preprocess(input)
        return doRounds(monkeys, 20)
    }

    fun part2(input: String): BigInteger {
        divideByThree = false
        val monkeys = preprocess(input)
        return doRounds(monkeys, 10000)
    }

    println("#### DAY 11 ####")

    // test if implementation meets criteria from the description, like:
    val testInput = readInputToText("Day11_test")
    check(part1(testInput) == 10605.toBigInteger())
    check(part2(testInput) == 2713310158.toBigInteger())

    val input = readInputToText("Day11")
    //println("1: " + (part1(input) == 107822))
    //println("2: " + part2(input))
}

