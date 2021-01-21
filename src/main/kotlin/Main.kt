// For cards VisaMir & MasterMaestro
const val MONTH_LIMIT_CARD = 60_000_000
const val DAY_LIMIT_CARD = 15_000_000
// For VIsaMir
const val TRANSFER_FEE_VISA_MIR = 0.0075
const val MINIMUM_COMISSION_VISA_MIR = 3_500.00
// For MasterMaestro
const val TRANSFER_FEE_MC_ME = 0.006
const val PERMANENT_COMISSION_VISA_MIR = 2_000.00
const val ONCE_LIMIT_CARD = 7_500_000
// For VKPay
const val TRANSFER_FEE_VKPAY = 0.0
const val MONTH_LIMIT_ACC = 4_000_000
const val DAY_LIMIT_ACC = 1_500_000

fun main(args: Array<String>) {
    val amountFee = calcFee(amount = 8_000_000)
    val fee = Math.round(amountFee).toInt()
    when(fee) {
        -1, -2, -3 -> println("\n\t\tОперация невозможна. \nПричина - превышение лимита (дневного и/или месячного). " +
                "\nПопробуйте уменьшить сумму транзакции.")
        -4 -> println("\n\tОперация невозможна - указана неверная карта или счет.")
        else -> println("\n\tКомиссия к оплате: ${fee / 100}руб. ${fee % 100}коп.")
    }
}
// nameCardAccount == VKPay / VisaMir / MasterMaestro
fun calcFee(nameCardAccount: String = "VKPay", monthAmount: Int = 0, amount: Int): Double {
    return if(nameCardAccount == "VisaMir") {
        if(monthAmount + amount > MONTH_LIMIT_CARD || amount > DAY_LIMIT_CARD) {
            -1.0
        } else {
            if(amount * TRANSFER_FEE_VISA_MIR <= MINIMUM_COMISSION_VISA_MIR) MINIMUM_COMISSION_VISA_MIR
            else amount * TRANSFER_FEE_VISA_MIR
        }
    } else if (nameCardAccount == "MasterMaestro") {
        if(monthAmount + amount > MONTH_LIMIT_CARD || amount > DAY_LIMIT_CARD) {
            -2.0
        } else {
            if(amount + monthAmount <= ONCE_LIMIT_CARD) 0.0 else amount * TRANSFER_FEE_MC_ME + PERMANENT_COMISSION_VISA_MIR
        }
    } else if (nameCardAccount == "VKPay") {
        if(monthAmount + amount > MONTH_LIMIT_ACC || amount > DAY_LIMIT_ACC) {
            -3.0
        } else {
            amount * TRANSFER_FEE_VKPAY
        }
    } else {
        -4.0
    }
}