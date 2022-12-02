fun main() {
    val SELECTED_DAY = 1

    // Decide which Day to execute
    val allDays = arrayOf(
        ::Day01,
        ::Day02,
    )
    allDays[SELECTED_DAY - 1]()
}