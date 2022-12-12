fun solution(products: List<String>, product: String) {
    if (product !in products) {
        print("")
    } else {
        var result = ""
        for (i in 0..products.lastIndex) {
            if (products[i] == product) result += "$i "
        }
        print(result.substring(0, result.lastIndex))
    }
}