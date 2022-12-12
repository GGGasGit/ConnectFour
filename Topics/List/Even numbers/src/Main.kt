fun solution(numbers: List<Int>) {
    val result = mutableListOf<Int>()
    for (number in numbers) {
        if (number % 2 == 0) result.add(number)
    }
    print(result.joinToString(" "))
}