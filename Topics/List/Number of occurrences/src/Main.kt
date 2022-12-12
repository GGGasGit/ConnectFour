fun solution(strings: List<String>, str: String): Int {
    var occurrence = 0
    for (name in strings) {
        if (name == str) occurrence++
    }
    return occurrence
}