package com.example.skeletonapp.ui.features.baseviewmodel

import com.example.skeletonapp.data.DataProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChildVewModel @Inject constructor(dataProvider: DataProvider) :
    BaseViewModel(dataProvider) {

    val childData = "67890"

}