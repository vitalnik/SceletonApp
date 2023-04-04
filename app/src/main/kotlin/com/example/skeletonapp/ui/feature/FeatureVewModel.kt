package com.example.skeletonapp.ui.feature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FeatureVewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Home Fragment"
    }
    val text: LiveData<String> = _text
}