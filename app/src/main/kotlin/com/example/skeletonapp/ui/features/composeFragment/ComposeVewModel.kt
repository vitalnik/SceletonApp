package com.example.skeletonapp.ui.features.composeFragment

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ComposeVewModel @Inject constructor() : ViewModel() {

    private val _counterFlow = MutableStateFlow<Int>(0)
    val counterFlow = _counterFlow.asStateFlow()

    fun incrementCounter() {
        _counterFlow.value++
    }


}