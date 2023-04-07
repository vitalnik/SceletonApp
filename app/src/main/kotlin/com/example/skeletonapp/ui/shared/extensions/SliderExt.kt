package com.example.skeletonapp.ui.shared.extensions

import com.google.android.material.slider.Slider
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.max
import kotlin.math.roundToInt

private val minMaxNumberFormat = DecimalFormat("#")
private const val DEFAULT_STEP_SIZE = 1f

/**
 * Returns whether given [stepSize] is a factor of the range of the Slider.
 *
 * Checks if the slider step size value and the actual slider values are a factor of the
 * slider range value
 */
fun Slider.isStepSizeFactorOfRange(stepSize: Float) = stepSize != 0f &&
        valueFrom % stepSize == 0f &&
        valueTo % stepSize == 0f &&
        value % stepSize == 0f

/**
 * Set Slider settings based on given values.
 *
 * Sets the min, max, actual value, and the incremental values for the slider so the increment
 * and slider value is always a factor of the slider range value. In the case when incremental and
 * slider values are not a factor of the slider range value, the incremental and slider values will
 * be adjusted and rounded up or down to follow slider value restrictions.
 */
fun Slider.setMinAndMaxValues(
    minValue: Double?,
    maxValue: Double?,
    increment: Double?,
    value: Double?
) {
    if ((minValue == null) || (maxValue == null)) {
        return
    }
    if (minValue >= maxValue) {
        isEnabled = false
        return
    }

    minMaxNumberFormat.roundingMode = RoundingMode.CEILING
    valueFrom = minMaxNumberFormat.format(minValue).toFloat()
    stepSize = max(increment?.roundToInt()?.toFloat() ?: 0f, DEFAULT_STEP_SIZE)

    val range = maxValue - valueFrom
    val remainder = range % stepSize
    valueTo = if (remainder == 0.0) {
        maxValue.toFloat()
    } else {
        (maxValue - remainder).toFloat()
    }

    setNormalizedValue(value?.toFloat() ?: 0f)
}

fun Slider.setNormalizedValue(requestedValue: Float) {
    this.value = when {
        requestedValue <= valueFrom -> valueFrom
        requestedValue >= valueTo -> valueTo
        else -> {
            val valueRemainder = (requestedValue - valueFrom) % stepSize
            if (valueRemainder >= stepSize / 2) {
                requestedValue + (stepSize - valueRemainder)
            } else {
                requestedValue - valueRemainder
            }
        }
    }
}

