package com.example.skeletonapp.ui.shared

import android.text.Editable
import android.text.TextWatcher
import java.text.NumberFormat

/**
 * Number Text Watcher
 * @param minimumValue
 * @param maximumValue
 * @param currencySymbol
 * @param format
 * @param allowNegativeValue boolean to allow or not allow negative number values
 * @param callback
 */
class NumberTextWatcher(
    private val minimumValue: Double? = null,
    private val maximumValue: Double? = null,
    private val currencySymbol: String? = null,
    var format: Format = Format.DEFAULT,
    var allowNegativeValue: Boolean = false,
    private val callback: (result: String, cursor: Int, error: ErrorState?) -> Unit
): TextWatcher {

    companion object {
        private const val REMOVE_NON_DIGIT_REGEX_STRING = "[^\\d]"
        // keeps the negative string for negative numbers
        private const val REMOVE_NON_DIGIT_NEGATIVE_REGEX_STRING = "[^\\d-]"
        private const val EMPTY_STRING = ""
        private const val NEGATIVE_STRING = "-"

        fun removeNonDigitRegex() = REMOVE_NON_DIGIT_REGEX_STRING.toRegex()
        fun removeNonDigitNegativeRegex() = REMOVE_NON_DIGIT_NEGATIVE_REGEX_STRING.toRegex()
    }

    enum class Format {
        CURRENCY,
        PERCENTAGE,
        DEFAULT
    }

    private var lastString: String? = null
    private var lastError: ErrorState? = null
    private val maxValuePlaces: Int = if (maximumValue != null) {
        getFormattedCurrencyString(maximumValue, currencySymbol)?.length ?: 0
    } else {
        0
    }
    private val percentFormat by lazy {
        NumberFormat.getPercentInstance().apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        }
    }
    var mAmount: Double = 0.0

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        //If after is more than one, this is the reassignment to the field and we should ignore.
        if (after > 1) return
        lastString = s?.toString()

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        //If count is more than one, this is the reassignment to the field and we should ignore.
        if (count > 1) return
        //If count is one (normal non-deletion character input state) and we already are already
        //  above the maximum then ignore the input and send back the last string.
        if (count == 1 && ErrorState.ABOVE_MAXIMUM == lastError) {
            lastString?.let {
                callback(it, start + count, lastError)
            }
            return
        }
        val newChar = s?.toString()?.substring(start, start + count)

        sendFormattedValue(lastString, newChar, count)
    }

    fun sendFormattedValue(lastString: String?, newChar: String?, count: Int) {
        lastString?.let { last ->
            //Determine the new value to send back (add the new digit or remove the last digit
            //  depending on the input).

            // Remove everything except numbers (e.g. $ % , .)
            // allow "-" if the number can be negative
            val removeNonDigitRegex = if (allowNegativeValue) removeNonDigitNegativeRegex() else removeNonDigitRegex()
            var currentNumber = last.replace(removeNonDigitRegex, EMPTY_STRING)

            // If the new char is a number (and not a special character, alphabet, etc.)
            if (newChar?.matches(removeNonDigitRegex) == false) {
                currentNumber = when {
                    count > 0 -> {
                        // add the negative string to the front of the current number
                        // otherwise add the number to the end
                        if (allowNegativeValue && newChar == NEGATIVE_STRING) newChar + currentNumber else currentNumber + newChar
                    }
                    allowNegativeValue && currentNumber.startsWith(NEGATIVE_STRING) && newChar == EMPTY_STRING -> {
                        // remove the negative string from the current number on delete
                        currentNumber.removePrefix(NEGATIVE_STRING)
                    }
                    else -> {
                        currentNumber.substring(0, currentNumber.lastIndex)
                    }
                }
            }

            // If the number is blank, use zero by default
            currentNumber = currentNumber.takeIf { it.isNotBlank() } ?: "0.00"

            mAmount = currentNumber.toDouble() / 100.0
            val formattedNumber = when (format) {
                Format.CURRENCY -> getFormattedCurrencyString(mAmount, currencySymbol).toString()
                Format.PERCENTAGE -> getFormattedPercentString(mAmount)
                Format.DEFAULT -> mAmount.toString()
            }

            lastError = getErrors(mAmount)

            if (ErrorState.ABOVE_MAXIMUM == lastError && formattedNumber.length > maxValuePlaces) {
                callback(last, last.length, lastError)
            } else {
                callback(formattedNumber, formattedNumber.length, lastError)
            }
        }
    }

    private fun getFormattedPercentString(value: Double): String {
       return  percentFormat.format(value / 100)
    }

    private fun getErrors(number: Double) : ErrorState? {
        return when {
            number > maximumValue ?: number -> ErrorState.ABOVE_MAXIMUM
            number < minimumValue ?: number -> ErrorState.BELOW_MINIMUM
            else -> null
        }
    }

    enum class ErrorState {
        BELOW_MINIMUM,
        ABOVE_MAXIMUM
    }
}