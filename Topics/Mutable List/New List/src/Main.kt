fun solution(numbers: List<Int>, number: Int): MutableList<Int> {
    val result = numbers.toMutableList()
    result.add(number)
    return result
}