package Main
fun main(args: Array<String>) {
    println(countCommission(nowPay = 10000)) //0
}

fun countCommission(account: String = "VK pay", beforePay: Int = 0, nowPay: Int): Int {
    var commission = 0
    //when (account.toLowerCase()) { //систему можно указывать в любом регистре
    when {
        account.toLowerCase() == accountType.VKPAY.nameAcc.toLowerCase() && !isLimitForVKPay(beforePay, nowPay) -> commission = 0
        account.toLowerCase() == accountType.MASTERCARD.nameAcc.toLowerCase() && !isLimitForCardExceptVKPay(beforePay, nowPay) -> commission = commissionForMasterCardMaestro(beforePay, nowPay)
        account.toLowerCase() == accountType.MAESTRO.nameAcc.toLowerCase() && !isLimitForCardExceptVKPay(beforePay, nowPay) -> commission = commissionForMasterCardMaestro(beforePay, nowPay)
        account.toLowerCase() == accountType.VISA.nameAcc.toLowerCase() && !isLimitForCardExceptVKPay(beforePay, nowPay) -> commission = commissionForVisaMir(nowPay)
        account.toLowerCase() == accountType.MIR.nameAcc.toLowerCase() && !isLimitForCardExceptVKPay(beforePay, nowPay) -> commission = commissionForVisaMir(nowPay)
    }
    return commission
}

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