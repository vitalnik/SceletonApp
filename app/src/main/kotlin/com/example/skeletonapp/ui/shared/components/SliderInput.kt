package com.example.skeletonapp.ui.shared.components

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.TextView
import com.example.skeletonapp.R
import com.example.skeletonapp.databinding.SliderInputBinding
import com.example.skeletonapp.ui.shared.CurrencyTextWatcher
import com.example.skeletonapp.ui.shared.PercentTextWatcher
import com.example.skeletonapp.ui.shared.extensions.setMinAndMaxValues
import com.example.skeletonapp.ui.shared.extensions.setNormalizedValue
import com.example.skeletonapp.ui.shared.extensions.toPercentageString
import com.example.skeletonapp.ui.shared.getFormattedCurrencyString
import com.google.android.material.slider.Slider
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
        CURRENCY,
        PERCENT
    }

    private var binding: SliderInputBinding

    private lateinit var textWatcher: TextWatcher

    /**
     * Called when user stops inputting text or moving a slider.
     */
    lateinit var userInputCompletedCallback: ((value: Double) -> Unit)

    /**
     * The format of the field.
     */
    var fieldFormat: InputFieldFormat = InputFieldFormat.CURRENCY


    /**
     * Currency symbol.
     */
    var currencySymbol: String = ""

    init {
        binding = SliderInputBinding.inflate(LayoutInflater.from(context), this, true)
    }

    /**
     * Initializes component.
     */
    fun initializeSlider(
        min: Double?,
        max: Double?,
        increment: Double?,
        value: Double?,
    ) {
        with(binding) {
            slider.setMinAndMaxValues(min, max, increment, value)

            slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
                override fun onStartTrackingTouch(slider: Slider) {
                    //no-op
                }

                override fun onStopTrackingTouch(slider: Slider) {
                    userInputCompletedCallback(slider.value.toDouble())
                }
            })
            slider.addOnChangeListener { _, _, fromUser ->
                if (fromUser) {
                    updateInputTextWithNormalizedValue()
                }

                Log.d("TAG", ">>> slider.addOnChangeListener fromUser: $fromUser")
            }
        }

        initializeEditText()
        initializeLabels()
    }

    /**
     * Sets text input layout hint.
     */
    fun setInputHint(hint: String) {
        binding.inputLayout.hint = hint
    }

    /**
     * Updating input text with normalized slider's value (next tick value).
     */
    private fun applyNormalizedValue() {
        updateInputTextWithNormalizedValue()
        userInputCompletedCallback(binding.slider.value.toDouble())
    }

    private fun initializeEditText() {

        textWatcher = when (fieldFormat) {
            InputFieldFormat.PERCENT -> PercentTextWatcher(
                binding.editText
            ) {
                updateSlider(it)
            }
            InputFieldFormat.CURRENCY -> CurrencyTextWatcher(
                binding.editText,
                currencySymbol,
            ) {
                updateSlider(it)
            }
        }

        binding.editText.setOnEditorActionListener(
            TextView.OnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    applyNormalizedValue()
                    return@OnEditorActionListener true
                }
                false
            }
        )

        binding.editText.addTextChangedListener(textWatcher)

        binding.editText.setText(binding.slider.value.roundToLong().toString())
    }

    private fun updateSlider(value: Double) {
        binding.slider.setNormalizedValue(value.toFloat())
    }

    private fun initializeLabels() {

        val valueFrom = binding.slider.valueFrom.toDouble()
        val valueTo = binding.slider.valueTo.toDouble()
        val stepSize = binding.slider.stepSize.toDouble()

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
    }

    private fun updateInputTextWithNormalizedValue() {
        with(binding) {
            with(editText) {

                Log.d("TAG", ">>> SliderInput updateInputTextWithNormalizedValue $textWatcher")

                setText(getFormattedValue(slider.value.toDouble()))

                requestFocus()
                setSelection(length())
            }
        }
    }

    private fun getFormattedValue(value: Double): String =
        when (fieldFormat) {
            InputFieldFormat.PERCENT -> value.toPercentageString()
            InputFieldFormat.CURRENCY -> getFormattedCurrencyString(value, currencySymbol)
                ?: ""
        }

    override fun setEnabled(enabled: Boolean) {
        with(binding) {
            editText.isEnabled = enabled
            slider.isEnabled = enabled
        }
        super.setEnabled(enabled)
    }
}
