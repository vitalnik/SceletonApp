package com.example.skeletonapp.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skeletonapp.data.DataProvider
import com.example.skeletonapp.managers.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataProvider: DataProvider,
    private val sessionManager: SessionManager,
) : ViewModel() {

    private val _displayData = MutableStateFlow<List<String>>(emptyList())
    val displayData = _displayData.asStateFlow()

    val sessionFlow = sessionManager.sessionFlow

    fun loadData() {
        viewModelScope.launch {
            _displayData.value = dataProvider.getData().flatMap { dataItem ->
                dataItem.values.filter {
                    it.endsWith("111")
                }
            }
        }
    }

}