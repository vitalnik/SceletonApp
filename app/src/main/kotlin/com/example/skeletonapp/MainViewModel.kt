package com.example.skeletonapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.managers.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sessionManager: SessionManager
): ViewModel() {

    private val _dataSource = MutableStateFlow<Int>(0)
    val dataSource = _dataSource.asStateFlow()

    val sessionFlow = sessionManager.sessionFlow

    fun isLoggedIn() = sessionFlow.value

    fun toggleSessionState() {

        viewModelScope.launch {
            if (sessionManager.isLoggedIn()) {
                sessionManager.logOut()
            } else {
                sessionManager.logIn()
            }
        }

    }

}