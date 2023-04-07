package com.example.skeletonapp.ui.feature.sliderInputFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SliderInputVewModel : ViewModel() {

    private val _textValueFlow = MutableStateFlow<String>("4000.0")
    val textValueFlow = _textValueFlow.asStateFlow()

    var updateTextValueJob: Job? = null

    fun updateTextValue(value: String, delay: Long = 0) {
        updateTextValueJob?.cancel()
        updateTextValueJob = viewModelScope.launch {
            delay(delay)
            _textValueFlow.emit(value)
        }
    }


}