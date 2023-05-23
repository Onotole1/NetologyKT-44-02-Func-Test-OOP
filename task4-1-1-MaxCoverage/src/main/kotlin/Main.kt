fun main(args: Array<String>) {
    println(countCommission("Мир", 0, 10000)) //75
    println(countCommission("Мир", 0, 1000)) //35
    println(countCommission(nowPay = 10000)) //0
    println(countCommission("Maestro", 35000, 10000)) //0
    println(countCommission("Masetrcard", 100000, 10000)) //80
    println(countCommission("Masetrcard", 700000, 10000)) //лимит
    println(countCommission("VK pay", 700000, 10000)) //лимит
}

fun countCommission(account: String = "VK pay", beforePay: Int = 0, nowPay: Int): Int {
    var commission = 0
    //when (account.toLowerCase()) { //систему можно указывать в любом регистре
    when {
        account.lowercase() == accountType.VKPAY.nameAcc && !isLimitForVKPay(beforePay, nowPay) -> commission = 0
        account.lowercase() == accountType.MASTERCARD.nameAcc && !isLimitForCardExceptVKPay(beforePay, nowPay) ->
            commission = commissionForMasterCardMaestro(beforePay, nowPay)
        account.lowercase() == accountType.MAESTRO.nameAcc && !isLimitForCardExceptVKPay(beforePay, nowPay) ->
            commission = commissionForMasterCardMaestro(beforePay, nowPay)
        account.lowercase() == accountType.VISA.nameAcc && !isLimitForCardExceptVKPay(beforePay, nowPay) ->
            commission = commissionForVisaMir(nowPay)
        account.lowercase() == accountType.MIR.nameAcc && !isLimitForCardExceptVKPay(beforePay, nowPay) ->
            commission = commissionForVisaMir(nowPay)
    }
    return commission}

fun commissionForMasterCardMaestro(beforePay: Int, nowPay: Int): Int = if (beforePay < 75000) 0
else (nowPay * 0.006 + 20).toInt()

fun commissionForVisaMir(nowPay: Int): Int = if (nowPay * 0.0075 < 35) 35
else (nowPay * 0.0075).toInt()

fun isLimitForCardExceptVKPay(beforePay: Int, nowPay: Int):Boolean{
    if((beforePay + nowPay) > 600_000 || nowPay > 150_000) {
        println("Превышен лимит перевода")
        return true
    }
    return false
}

fun isLimitForVKPay(beforePay: Int, nowPay: Int):Boolean{
    if((beforePay + nowPay) > 40_000 || nowPay > 15_000) {
        println("Превышен лимит перевода")
        return true
    }
    return false
}

enum class accountType(var nameAcc: String) {
    VKPAY("VK pay"),
    MASTERCARD("Masetrcard"),
    MAESTRO("Maestro"),
    VISA("Visa"),
    MIR("Мир")
}