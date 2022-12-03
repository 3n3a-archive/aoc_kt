fun main() {
    val SELECTED_DAY = 3

    // Decide which Day to execute
    val allDays = arrayOf(
        ::Day01,
        ::Day02,
        ::Day03,
    )
    allDays[SELECTED_DAY - 1]()
}