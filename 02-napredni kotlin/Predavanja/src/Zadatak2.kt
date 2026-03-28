//funkcijski tipovi
fun main(){
    operate(2, 2, {a, b -> a + b})
    operate(2, 2, {a, b -> a * b})
    operate(2, 2, {a, b -> if(a > b) a else b })
}
fun operate(a: Int, b: Int, operation: (Int, Int) -> Int){
    println( operation(a, b) )
}