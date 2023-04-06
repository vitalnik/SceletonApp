package com.example.skeletonapp.ui.shared

import java.text.DecimalFormat

private const val HUNDRED = 100
private const val PERCENTAGE_FORMAT = "0.###%"

/**
 * Converting Double to Percentage string using default locale.
 */
fun Double?.toPercentageString(pattern: String = PERCENTAGE_FORMAT): String =
    DecimalFormat(
        pattern
    ).format(
        (this ?: 0.0) / HUNDRED
    )

/**
 * Converting Float to Percentage string.
 */
fun Float?.toPercentageString(): String =
    this?.toDouble().toPercentageString()
