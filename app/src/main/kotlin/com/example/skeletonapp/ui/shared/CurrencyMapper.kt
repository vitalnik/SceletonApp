package com.example.skeletonapp.ui.shared

import java.text.DecimalFormat
import java.text.NumberFormat

/**
 * Formats the [amount] with a [currencySymbol] into a formatted currency string.
 */
fun getFormattedCurrencyString(amount: Double, currencySymbol: String?): String? {
    val formatter: DecimalFormat = getCurrencyFormat(currencySymbol)
    formatter.minimumFractionDigits = 2
    return formatter.format(amount)
}

/**
 * Returns the DecimalFormat with [currencySymbol] for this device's locale.
 */
fun getCurrencyFormat(currencySymbol: String?): DecimalFormat {
    val currencyFormat = NumberFormat.getCurrencyInstance() as DecimalFormat
    val symbols = currencyFormat.decimalFormatSymbols
    symbols.currencySymbol = currencySymbol
    currencyFormat.decimalFormatSymbols = symbols
    return currencyFormat
}
