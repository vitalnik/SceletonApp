package com.example.skeletonapp.ui.shared.components

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import com.example.skeletonapp.R
import com.example.skeletonapp.databinding.SliderInputBinding
import com.example.skeletonapp.ui.feature.nestedFragment.setMinAndMaxValues
import com.example.skeletonapp.ui.shared.getFormattedCurrencyString
import com.example.skeletonapp.ui.shared.toPercentageString
import com.google.android.material.slider.Slider
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToLong

/**
 * Custom component combining EditText and Slider.
 * Allows editing input text either by typing or moving the slider.
 */
class SliderInput(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    /**
     * Input field format enum.
     */
    enum class InputFieldFormat {
        NUMBER,
        CURRENCY,
        PERCENT,
    }

    private var binding: SliderInputBinding

    /**
     * Called when user stops inputting text or moving a slider.
     */
    lateinit var userInputStoppedCallback: ((value: Double) -> Unit)

    private val handler = Handler(Looper.getMainLooper())

    private val valueFrom
        get() = binding.slider.valueFrom.toDouble()

    private val valueTo
        get() = binding.slider.valueTo.toDouble()

    private val stepSize
        get() = binding.slider.stepSize.toDouble()

    /**
     * The format of the field.
     */
    var fieldFormat: InputFieldFormat = InputFieldFormat.NUMBER

    /**
     * Currency symbol.
     */
    var currencySymbol: String = "$"

    init {
        binding = SliderInputBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private val inputFinishedRunnable = Runnable {
        updateTextEditWithSliderValue()
        userInputStoppedCallback(binding.slider.value.toDouble())

        hideKeyboard(binding.editText)
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private val textWatcher: TextWatcher = object : TextWatcher {

        private var fromUser = true

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // no-op
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            fromUser = (binding.editText.text?.length ?: 0) > count
        }

        override fun afterTextChanged(editable: Editable) {
            if (fromUser) {
                val inputValue = if (editable.isEmpty()) 0.0 else editable.toString().toDouble()
                val normalizedValue = getNormalizedValue(inputValue)

                binding.slider.value = normalizedValue.toFloat()

                updateFormattedText()

                updateTextEditWithNormalizedValue()
            }
        }
    }

    private fun updateTextEditWithNormalizedValue() {
        handler.removeCallbacks(inputFinishedRunnable)
        handler.postDelayed(inputFinishedRunnable, USER_STOPPED_TYPING_DELAY)
    }

    /**
     * Initializes component.
     */
    fun initialize(
        min: Double?,
        max: Double?,
        increment: Double?,
        value: Double?,
    ) {

        this.currencySymbol = currencySymbol
        this.fieldFormat = fieldFormat

        binding.slider.setMinAndMaxValues(min, max, increment, value)
        binding.slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
                //no-op
            }

            override fun onStopTrackingTouch(slider: Slider) {
                userInputStoppedCallback(slider.value.toDouble())
            }
        })
        binding.slider.addOnChangeListener { _, sliderValue, fromUser ->
            if (fromUser) {
                handler.removeCallbacks(inputFinishedRunnable)
                updateTextEditWithSliderValue()
                updateFormattedText()
            }
        }

        initializeEditText()
        initializeLabels()
    }

    private fun initializeEditText() {
        binding.editText.addTextChangedListener(textWatcher)
        binding.editText.setText(binding.slider.value.roundToLong().toString())
    }

    private fun initializeLabels() {
        binding.formattedText.alpha = DISABLED_ALPHA

        binding.bottomLabel.apply {
            text = if (fieldFormat == InputFieldFormat.PERCENT) {
                val percentageFormatPattern = "#%"
                context.getString(
                    R.string.election_card_allowed_contribution_range_with_increment_label,
                    valueFrom.toPercentageString(pattern = percentageFormatPattern),
                    valueTo.toPercentageString(pattern = percentageFormatPattern),
                    stepSize.toPercentageString(pattern = percentageFormatPattern)
                )
            } else {
                context.getString(
                    R.string.election_card_allowed_contribution_range_with_increment_label,
                    getFormattedCurrencyString(
                        valueFrom,
                        currencySymbol
                    ),
                    getFormattedCurrencyString(
                        valueTo,
                        currencySymbol
                    ),
                    getFormattedCurrencyString(
                        stepSize,
                        currencySymbol
                    )
                )
            }
        }

        updateFormattedText()
    }

    /**
     * Sets text input layout hint.
     */
    fun setInputHint(hint: String) {
        binding.inputLayout.hint = hint
    }

    private fun getNormalizedValue(value: Double): Double = when {
        value <= valueFrom -> valueFrom
        value >= valueTo -> valueTo
        else -> {
            val valueRemainder = (value - valueFrom) % stepSize
            if (valueRemainder >= stepSize / 2) {
                min((value + (stepSize - valueRemainder)), valueTo)
            } else {
                max((value - valueRemainder), valueFrom)
            }
        }
    }

    private fun updateFormattedText() {
        val value = binding.slider.value.toDouble()
        when (fieldFormat) {
            InputFieldFormat.NUMBER -> {
                binding.formattedText.text = value.toString()
            }
            InputFieldFormat.CURRENCY -> {
                binding.formattedText.text = getFormattedCurrencyString(
                    value, currencySymbol
                )
            }
            InputFieldFormat.PERCENT -> {
                binding.formattedText.text = value.toPercentageString()
            }
        }
    }

    private fun updateTextEditWithSliderValue() {
        with(binding) {
            with(editText) {
                setText(slider.value.roundToLong().toString())
                requestFocus()
                setSelection(length())
            }
        }
    }

    override fun setEnabled(enabled: Boolean) {
        with(binding) {
            editText.isEnabled = enabled
            slider.isEnabled = enabled
        }
        super.setEnabled(enabled)
    }

    override fun onDetachedFromWindow() {
        handler.removeCallbacks(inputFinishedRunnable)
        super.onDetachedFromWindow()
    }

    companion object {
        private const val USER_STOPPED_TYPING_DELAY = 1500L
        private const val DISABLED_ALPHA = 0.5f
    }
}
