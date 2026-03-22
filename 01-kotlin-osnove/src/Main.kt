fun main() {
    zadatak1()
    zadatak2()
    zadatak3()
    zadatak4()
    zadatak5()
}


fun zadatak1(){
    val ime = "Leon"
    val prezime = "Šilipetar"
    var email: String? = null
    val dob: Int? = 22

    println("Duljina email adrese: ${email?.length ?: "email nije unesen"}")

    email = "leon.silipetar@gmail.com"

    println("Duljina email adrese: ${email?.length ?: "email nije unesen"}")
}


fun zadatak2(){
    val kod = 2
    val primljenNovac = 1.20

    val (odabrano, cijena)= when (kod) {
        1 -> "Voda" to 1.0
        2 -> "Cola" to 1.5
        3 -> "Sok" to 1.0
        4 -> "Kava" to 1.10
        else -> "Odaberi proizvod" to 0.0
    }

    if(primljenNovac >= cijena){
        val ostatak = primljenNovac - cijena
        println("Toči se $odabrano, ostatak: %.2f €".format(ostatak))
    } else {
        val nedostaje = cijena - primljenNovac
        println("Nedovoljno za $odabrano, nedostaje: %.2f".format(nedostaje))
    }
}


fun zadatak3(){
    val koraci = listOf(4500, 12000, 8000, 15000, 3000, 11000, 9500)
    var ukupno = 0

    for(i in 0 until koraci.size){
        ukupno += koraci[i]
    }

    println("Broj koraka u ${koraci.size} dana: $ukupno")

    var i = 0
    var pronadjen = false

    while(i < koraci.size){
        if(koraci[i] > 10000){
            println("Prvi dan s više od 10000 koraka: ${i + 1}")
            pronadjen = true
            break
        }
        i++
    }
    if(!pronadjen){ println("Nema dana s više od 10000 koraka") }
}


fun zadatak4(){
    println("Unesite korisničko ime: ")
    var username = readln()
    val prepared = prepare(username)
    val valid  = isValid(prepared)

    println("Korisničko ime: $username" +
            "\nPripremljeno: $prepared" +
            "\nValjano: $valid")
}

fun prepare(username: String): String {
    return username.trim().lowercase()
}

fun isValid(username: String): Boolean{
    if(username.isBlank()) return false
    if(username.length !in 5..15) return false
    if(!username.first().isLetter()) return false
    if(!username.all { it.isLetterOrDigit() || it == '_' }) return false
    if(username.contains(' ')) return false
    return true
}


fun zadatak5() {
    val racun1 = BankAccount("001")
    val racun2 = BankAccount("002")

    racun1.deposit(100.0)
    racun1.withdraw(30.0)
    racun1.withdraw(100.0) // nedovoljno novca

    racun2.deposit(50.0)
    racun2.withdraw(20.0)

    println("Stanje računa ${racun1.accountNumber}: ${racun1.balance} €")
    println("Stanje računa ${racun2.accountNumber}: ${racun2.balance} €")
    println("Ukupan broj računa: ${BankAccount.totalAccounts}")
}

object TransactionLogger{
    fun log(message: String){
        println("LOG: $message")
    }
}

class BankAccount(
    val accountNumber: String
) {
    var balance: Double = 0.0
        private set

    init{
        totalAccounts++
    }

    fun deposit(amount: Double){
        if(amount <= 0.0){
            TransactionLogger.log("Pokušaj uplate negativnog ili nula iznosa: $amount na $accountNumber")
            return
        }
        balance += amount
        TransactionLogger.log("Uplata $amount € na $accountNumber, novo stanje: $balance €")
    }

    fun withdraw(amount: Double) {
        if (amount <= 0) {
            TransactionLogger.log("Pokušaj isplate negativnog ili nula iznosa: $amount na $accountNumber")
            return
        }
        if (amount > balance) {
            TransactionLogger.log("Nedovoljno sredstava za isplatu $amount € sa računa $accountNumber")
            return
        }
        balance -= amount
        TransactionLogger.log("Isplata $amount € sa računa $accountNumber. Novo stanje: $balance €")
    }

    companion object{
        var totalAccounts: Int = 0
            private set
    }
}