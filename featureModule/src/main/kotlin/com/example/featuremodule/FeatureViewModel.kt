package com.example.featuremodule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.managers.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeatureViewModel @Inject constructor(
    private val sessionManager: SessionManager
): ViewModel() {

    val sessionFlow = sessionManager.sessionFlow

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