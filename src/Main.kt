fun main() {
    val SELECTED_DAY = 11

    // Decide which Day to execute
    val allDays = arrayOf(
        ::Day01,
        ::Day02,
        ::Day03,
        ::Day04,
        ::Day05,
        ::Day06,
        ::Day04, // 7
        ::Day08,
        ::Day08, // 9
        ::Day10,
        ::Day11,
    )
    allDays[SELECTED_DAY - 1]()
}