package com.example.skeletonapp.ui.shared

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

/**
 * Formats the [amount] with a [currencySymbol] into a formatted currency string.
 */
fun getFormattedCurrencyString(
    amount: Double,
    currencySymbol: String = "",
    currencyCode: String = "",
    locale: Locale = Locale.getDefault()
): String? {

    var formatLocale = locale
    if ((currencyCode == "CAD" || currencyCode == "USD") && locale == Locale.CANADA) {
        //formatLocale = Locale.US
    }

    val formatter: DecimalFormat = getCurrencyFormat(currencySymbol, currencyCode, formatLocale)
    formatter.minimumFractionDigits = 2
    return formatter.format(amount)
}

/**
 * Returns the DecimalFormat with [currencySymbol] for this device's locale.
 */
fun getCurrencyFormat(
    currencySymbol: String = "",
    currencyCode: String = "",
    locale: Locale = Locale.getDefault()
): DecimalFormat {

    val currencyFormat = NumberFormat.getCurrencyInstance(locale) as DecimalFormat
    val symbols = currencyFormat.decimalFormatSymbols

    symbols.currencySymbol = currencySymbol
    //symbols.internationalCurrencySymbol = currencyCode
    symbols.currency = Currency.getInstance(currencyCode)

    currencyFormat.decimalFormatSymbols = symbols
    return currencyFormat
}
