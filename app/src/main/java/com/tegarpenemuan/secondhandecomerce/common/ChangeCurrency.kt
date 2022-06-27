package com.tegarpenemuan.secondhandecomerce.common

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

object ChangeCurrency {
    fun gantiRupiah(string: String): String {
        return NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(Integer.valueOf(string))
    }
    fun idrFormat(number: Int): String{
        val decimalFormat: NumberFormat = DecimalFormat("#,###")
        return decimalFormat.format(number)
    }
}