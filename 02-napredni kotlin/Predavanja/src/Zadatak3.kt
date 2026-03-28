//Generics
//kolekcije
fun main(){
    val age = listOf(15, 17, 18, 20, 21, 22)

    for(age in age){
        if(age >= 18) println(age)
    }

    val listOfAdults = age.filter { it > 18 }.map {"Adult" to it}
    println(listOfAdults)

    val names = listOf("Ana", "Ivan", "Marko", "Leon")
    val grouped = names.groupBy { it.first() }
    println(grouped.keys)
    val counted = names.groupingBy { it.first() }.eachCount()
    println(counted)
}

//data klase
// data class User(...){}
