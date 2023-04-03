package com.example.skeletonapp

import androidx.lifecycle.ViewModel
import com.example.skeletonapp.managers.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sessionManager: SessionManager
): ViewModel() {

    private val _dataSource = MutableStateFlow<Int>(0)
    val dataSource = _dataSource.asStateFlow()

}