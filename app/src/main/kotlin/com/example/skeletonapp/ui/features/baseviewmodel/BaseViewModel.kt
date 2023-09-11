package com.example.skeletonapp.ui.features.baseviewmodel

import androidx.lifecycle.ViewModel
import com.example.skeletonapp.data.DataProvider

abstract class BaseViewModel(
    private val dataProvider: DataProvider,
) : ViewModel() {

    fun getParentData() = dataProvider.getData2()

}