fun names(namesList: List<String>): List<String> {
    val nameCount = mutableListOf<String>()
    val names = namesList.toSet()
    for (name in names) {
        nameCount.add("The name $name is repeated ${namesList.count{ it == name }} times")
    }
    return nameCount
}