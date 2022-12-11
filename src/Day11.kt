import com.ionspin.kotlin.bignum.decimal.BigDecimal
import com.ionspin.kotlin.bignum.decimal.DecimalMode
import com.ionspin.kotlin.bignum.decimal.RoundingMode
import com.ionspin.kotlin.bignum.integer.BigInteger
import com.ionspin.kotlin.bignum.integer.toBigInteger
import com.ionspin.kotlin.bignum.modular.ModularBigInteger
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

data class Monkey(
    val index: Int,
    var startingItems: MutableList<BigInteger>,
    val operation: String,
    val testDivisibleBy: BigInteger,
    val ifTrue: Int,
    val ifFalse: Int,
    var inspectedItemsCount: BigInteger,
)
@OptIn(ExperimentalTime::class)
fun Day11() {
    fun divideByThree(number: BigInteger): BigInteger {
        return BigDecimal.fromBigInteger(number).divide(BigDecimal.fromInt(3), DecimalMode(5, RoundingMode.FLOOR, 0)).toBigInteger()
    }

    var lcm: BigInteger = 0.toBigInteger()

    fun largestCommonModulo(number: BigInteger): BigInteger {
        return number.mod(lcm)
    }

    var reductionFunc = ::divideByThree
    var roundCount = 0


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
                0.toBigInteger()
            ))
        }

        return monkeys
    }

    fun doCalculation(first: BigInteger, second: BigInteger, operation: String): BigInteger {
        when(operation) {
            "+" -> {
                return first + second
            }
            "*" -> {
                return first * second
            }
        }
        return 0.toBigInteger()
    }

    fun replaceWithBigIntIfOld(numberString: String, replacement: BigInteger): BigInteger {
        return if (numberString == "old") {
            replacement
        } else {
            numberString.toBigInteger()
        }
    }

    fun inspectStartingItems(items: MutableList<BigInteger>, operation: String): List<BigInteger> {
        val newList = mutableListOf<BigInteger>()

        // inspect each item
        for (item in items) {
            // apply operation to item
            val (firstItem, calcOperation, secondItem) = operation.split(" ")

            val firstNumber = replaceWithBigIntIfOld(firstItem, item)
            val secondNumber = replaceWithBigIntIfOld(secondItem, item)

            val calcResult: BigInteger = doCalculation(firstNumber, secondNumber, calcOperation)

            // divide by 3
            var reducedResult = reductionFunc(calcResult)

            //println("Calc: $firstNumber $calcOperation $secondNumber = $calcResult")

            newList.add(reducedResult)
        }
        return newList
    }

    fun largestCommonModulo(monkeys: List<Monkey>): BigInteger {
        return monkeys.fold(1.toBigInteger()) { lcm, element -> lcm * element.testDivisibleBy }
    }

    fun executeTurn(monkey: Monkey, monkeys: MutableList<Monkey>): MutableList<Monkey> {
        // if no items return
        if (monkey.startingItems.size == 0) {
            return monkeys
        }

        val newItems = inspectStartingItems(monkey.startingItems, monkey.operation)
        monkeys.filter { it.index == monkey.index }[0].inspectedItemsCount += newItems.size

        // reset starting items for current monkey
        monkeys.filter { it.index == monkey.index }[0].startingItems = mutableListOf()

        // test each item
        for (item in newItems) {
            // test item
            // is divisible without remainder
            val testResult = (item % monkey.testDivisibleBy) == 0.toBigInteger()

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

    fun printMonkeys(monkeys: List<Monkey>, round: Int) {
        println("== After round $round ==")
        for (monkey in monkeys) {
            println("Monkey ${monkey.index} inspected items ${monkey.inspectedItemsCount} times.")
        }
        println()
    }

    fun doRounds(monkeysInput: MutableList<Monkey>, rounds: Int): BigInteger {
        var monkeys = monkeysInput

        for (i in 1..rounds) {
            roundCount += 1

            print("\n=== ROUND $i ===")
            val elapsed = measureTime {
                for (monkey in monkeys) {
                    monkeys = executeTurn(monkey, monkeys)
                }
            }
            println("$elapsed.")
        }

        monkeys.sortBy { it.inspectedItemsCount }
        monkeys.reverse()

        val topMonkeys = monkeys.take(2)
        var levelOfMonkeyBiz: BigInteger = topMonkeys[0].inspectedItemsCount * topMonkeys[1].inspectedItemsCount

        println(monkeys)
        println("Level of Monkey Biz: $levelOfMonkeyBiz")
        return levelOfMonkeyBiz
    }

    fun part1(input: String): BigInteger {
        val monkeys = preprocess(input)
        return doRounds(monkeys, 20)
    }

    fun part2(input: String): BigInteger {
        val monkeys = preprocess(input)
        reductionFunc = ::largestCommonModulo
        lcm = largestCommonModulo(monkeys)
        return doRounds(monkeys, 10000)
    }

    println("#### DAY 11 ####")

    // test if implementation meets criteria from the description, like:
    val testInput = readInputToText("Day11_test")
    check(part1(testInput) == 10605L.toBigInteger())
    check(part2(testInput) == 2713310158L.toBigInteger())

    val input = readInputToText("Day11")
    println("1: " + (part1(input) == 107822L.toBigInteger()))
    println("2: " + (part2(input) == 27267163742L.toBigInteger()))
}

