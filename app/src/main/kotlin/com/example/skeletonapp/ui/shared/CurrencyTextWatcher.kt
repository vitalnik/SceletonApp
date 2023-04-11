package com.example.skeletonapp.ui.shared

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.text.ParseException
import java.util.Locale
import kotlin.math.min

class CurrencyTextWatcher(
    private val editText: EditText,
    private val currencySymbol: String,
    private val maxNumberOfDecimalPlaces: Int = 2,
    val onUpdateFromUser: (value: Double) -> Unit = {}
) : TextWatcher {

    private var removeAtIndex = -1
    private var removeCount = 0
    private var fromUser = true

    private var hasDecimalPoint = false
    private val wholeNumberDecimalFormat =
        (NumberFormat.getNumberInstance(Locale.getDefault()) as DecimalFormat).apply {
            applyPattern("#,##0")
        }

    private val fractionDecimalFormat =
        (NumberFormat.getNumberInstance(Locale.getDefault()) as DecimalFormat)

    private val decimalFormatSymbols: DecimalFormatSymbols
        get() = wholeNumberDecimalFormat.decimalFormatSymbols

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        if (s.toString().substring(start, start + count) ==
            decimalFormatSymbols.groupingSeparator.toString()
        ) {
            removeAtIndex = start
            removeCount = count
        } else {
            removeAtIndex = -1
            removeCount = 0
        }

        fractionDecimalFormat.isDecimalSeparatorAlwaysShown = true
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        hasDecimalPoint = s.toString().contains(decimalFormatSymbols.decimalSeparator.toString())
        fromUser = (editText.text?.length ?: 0) > count
    }

    override fun afterTextChanged(s: Editable) {

        var newInputString = s.toString()
        if (removeAtIndex > 0) {
            newInputString =
                newInputString.removeRange(removeAtIndex - 1, removeAtIndex + removeCount - 1)
        }

        if (newInputString.length < currencySymbol.length) {
            editText.setText(currencySymbol)
            editText.setSelection(currencySymbol.length)
            return
        }

        if (newInputString == currencySymbol) {
            editText.setSelection(currencySymbol.length)
            return
        }

        editText.removeTextChangedListener(this)
        val startLength = editText.text.length

        var numberWithoutGroupingSeparator =
            parseMoneyValue(
                newInputString,
                decimalFormatSymbols.groupingSeparator.toString(),
                currencySymbol
            )

        if (numberWithoutGroupingSeparator == decimalFormatSymbols.decimalSeparator.toString()) {
            numberWithoutGroupingSeparator = "0$numberWithoutGroupingSeparator"
        }

        numberWithoutGroupingSeparator = truncateNumberToMaxDecimalDigits(
            numberWithoutGroupingSeparator,
            maxNumberOfDecimalPlaces,
            decimalFormatSymbols.decimalSeparator
        )

        val parsedNumber = try {
            fractionDecimalFormat.parse(numberWithoutGroupingSeparator)
        } catch (e: ParseException) {
            0.0
        }
        val selectionStartIndex = editText.selectionStart

        if (hasDecimalPoint) {
            fractionDecimalFormat.applyPattern(
                FRACTION_FORMAT_PATTERN_PREFIX +
                        getFormatSequenceAfterDecimalSeparator(numberWithoutGroupingSeparator)
            )
            editText.setText("$currencySymbol${fractionDecimalFormat.format(parsedNumber)}")
        } else {
            editText.setText("$currencySymbol${wholeNumberDecimalFormat.format(parsedNumber)}")
        }

        val endLength = editText.text.length
        val selection = selectionStartIndex + (endLength - startLength)

        if (selection > 0 && selection <= editText.text.length) {
            editText.setSelection(selection)
        } else {
            editText.setSelection(editText.text.length - 1)
        }

        if (fromUser) {
            onUpdateFromUser(parsedNumber.toDouble())
        }

        editText.addTextChangedListener(this)
    }

    private fun getFormatSequenceAfterDecimalSeparator(number: String): String {
        val noOfCharactersAfterDecimalPoint =
            number.length - number.indexOf(decimalFormatSymbols.decimalSeparator) - 1
        return "0".repeat(min(noOfCharactersAfterDecimalPoint, maxNumberOfDecimalPlaces))
    }

    private fun truncateNumberToMaxDecimalDigits(
        number: String,
        maxDecimalDigits: Int,
        decimalSeparator: Char
    ): String {
        val arr = number
            .split(decimalSeparator)
            .toMutableList()
        if (arr.size != 2) {
            return number
        }
        arr[1] = arr[1].take(maxDecimalDigits)
        return arr.joinToString(separator = decimalSeparator.toString())
    }

    private fun parseMoneyValue(
        value: String,
        groupingSeparator: String,
        currencySymbol: String
    ): String =
        value.replace(groupingSeparator, "").replace(currencySymbol, "")

    companion object {
        const val FRACTION_FORMAT_PATTERN_PREFIX = "#,##0."
    }

}

