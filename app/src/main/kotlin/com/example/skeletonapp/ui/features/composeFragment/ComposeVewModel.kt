package com.example.skeletonapp.ui.features.composeFragment

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ComposeVewModel : ViewModel() {

    private val _counterFlow = MutableStateFlow<Int>(0)
    val counterFlow = _counterFlow.asStateFlow()

    fun incrementCounter() {
        _counterFlow.value++
    }


}