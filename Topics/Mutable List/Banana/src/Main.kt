fun solution(strings: MutableList<String>, str: String): MutableList<String> {
    val result = mutableListOf<String>()
    for (string in strings) {
        result.add(string.replace(str, "Banana"))
    }
    return result
}