package com.example.skeletonapp.ui.features.twowaybinding

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class TwoWayBindingViewModel @Inject constructor() : ViewModel() {

    val textValueFlow = MutableStateFlow<String>("some text")

    val fields = listOf(
        Pair("label 1", MutableStateFlow("value 1")),
        Pair("label 2", MutableStateFlow("value 2"))
    )

    fun checkValue(position: Int) {

        Log.d("TAG", ">>> value at position $position = ${fields[position].second.value}")

    }

}