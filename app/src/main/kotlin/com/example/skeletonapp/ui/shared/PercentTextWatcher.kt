package com.example.skeletonapp.ui.shared

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.example.skeletonapp.ui.shared.extensions.toPercentageString

/**
 * A simple number text watcher.
 */
class PercentTextWatcher(
    private val editText: EditText,
    private val onUpdateFromUser: (value: Double) -> Unit
) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        // no-op
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(editable: Editable) {

        val parsedValue = parsePercentValue(editable.toString())

        if (parsedValue.isEmpty()) {
            return
        }

        editText.removeTextChangedListener(this)

        editText.setText(parsedValue.toDouble().toPercentageString())
        editText.setSelection(editText.text.length - 1)

        onUpdateFromUser(parsedValue.toDouble())

        editText.addTextChangedListener(this)
    }

    private fun parsePercentValue(
        value: String,
    ): String = if (value.isEmpty()) "0"
    else
        value.replace(" ", "").replace("%", "")

}
