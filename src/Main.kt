fun main() {
    val SELECTED_DAY = 4

    // Decide which Day to execute
    val allDays = arrayOf(
        ::Day01,
        ::Day02,
        ::Day03,
        ::Day04,
    )
    allDays[SELECTED_DAY - 1]()
}