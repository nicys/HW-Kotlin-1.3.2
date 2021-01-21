// For cards Visa / MIR / MasterCard / Maestro
const val MONTH_LIMIT_CARD = 60_000_000
const val DAY_LIMIT_CARD = 15_000_000
const val EXCEED_LIMIT = -1.0
const val INVALID_CARD_ACCOUNT = -2.0
// For VIsa & MIR
const val TRANSFER_FEE_VISA_MIR = 0.0075
const val MINIMUM_COMISSION_VISA_MIR = 3_500.00
// For MasterCard & Maestro
const val TRANSFER_FEE_MC_ME = 0.006
const val PERMANENT_COMISSION_MC_ME = 2_000.00
const val ONCE_LIMIT_CARD = 7_500_000
// For VKPay
const val TRANSFER_FEE_VKPAY = 0.0
const val MONTH_LIMIT_ACC = 4_000_000
const val DAY_LIMIT_ACC = 1_500_000

fun main(args: Array<String>) {
    val amountFee = calcFee(amount = 8_000_000)
    val fee = Math.round(amountFee).toInt()
    printResults(fee)
}

fun calcFee(nameCardAccount: String = "MasterCard", monthAmount: Int = 0, amount: Int): Double {
    return when (nameCardAccount) {
        "Visa" -> calcVisaMir(monthAmount, amount)
        "MIR" -> calcVisaMir(monthAmount, amount)
        "MasterCard" -> calcVisaMir(monthAmount, amount)
        "Maestro" -> calcVisaMir(monthAmount, amount)
        "VKPay" -> calcVkPay(monthAmount, amount)
        else -> INVALID_CARD_ACCOUNT
    }
}

fun calcVisaMir(monthAmount: Int = 0, amount: Int): Double {
    return if(monthAmount + amount > MONTH_LIMIT_CARD || amount > DAY_LIMIT_CARD) {
        EXCEED_LIMIT
    } else {
        if(amount * TRANSFER_FEE_VISA_MIR <= MINIMUM_COMISSION_VISA_MIR) MINIMUM_COMISSION_VISA_MIR else amount * TRANSFER_FEE_VISA_MIR
    }
}

fun calcMasterMaestro(monthAmount: Int = 0, amount: Int): Double {
    return if(monthAmount + amount > MONTH_LIMIT_CARD || amount > DAY_LIMIT_CARD) {
        EXCEED_LIMIT
    } else {
        if(amount + monthAmount <= ONCE_LIMIT_CARD) 0.0 else amount * TRANSFER_FEE_MC_ME + PERMANENT_COMISSION_MC_ME
    }
}

fun calcVkPay(monthAmount: Int = 0, amount: Int): Double {
    return if(monthAmount + amount > MONTH_LIMIT_ACC || amount > DAY_LIMIT_ACC) {
        EXCEED_LIMIT
    } else {
        amount * TRANSFER_FEE_VKPAY
    }
}

fun printResults(fee: Int) {
    when(fee) {
        EXCEED_LIMIT.toInt() -> println("\n\t\tОперация невозможна. \nПричина - превышение лимита (дневного и/или месячного). " +
                "\nПопробуйте уменьшить сумму транзакции.")
        INVALID_CARD_ACCOUNT.toInt() -> println("\n\tОперация невозможна - указана неверная карта или счет.")
        else -> println("\n\tКомиссия к оплате: ${fee / 100}руб. ${fee % 100}коп.")
    }
}