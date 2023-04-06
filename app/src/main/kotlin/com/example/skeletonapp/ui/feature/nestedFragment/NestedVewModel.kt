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

    var updateTextValueJob: Job? = null

    fun updateTextValue(value: String, delay: Long = 0) {
        updateTextValueJob?.cancel()
        updateTextValueJob = viewModelScope.launch {
            delay(delay)
            _textValueFlow.emit(value)
        }
    }


}