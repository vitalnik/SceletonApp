package com.example.skeletonapp.ui.feature.nestedFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NestedVewModel : ViewModel() {

    private val _textValueFlow = MutableStateFlow<String>("4000.0")
    val textValueFlow = _textValueFlow.asStateFlow()

    val sliderMinValue = 1000.0
    val sliderMaxValue = 10000.0
    val sliderIncrement = 500.0
    val sliderValue = 4000.0

    var updateTextValueJob: Job? = null

    fun updateTextValue(value: String) {

        updateTextValueJob?.cancel()
        updateTextValueJob = viewModelScope.launch {
            delay(2000)
            _textValueFlow.value = value
        }

    }


}