package com.example.skeletonapp.ui.features.memoryLeaks

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow


class MemoryLeaksVewModel : ViewModel() {

    val list = MutableStateFlow<List<String>>(emptyList())

    init {
        list.value = listOf("Item 1", "Item 2", "Item 3")
    }

}