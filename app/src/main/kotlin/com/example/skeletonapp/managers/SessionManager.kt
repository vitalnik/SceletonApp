package com.example.skeletonapp.managers

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SessionManager @Inject constructor() {

    private val _sessionFlow = MutableStateFlow(false)
    val sessionFlow = _sessionFlow.asStateFlow()

    suspend fun logIn() {
        delay(1000)
        _sessionFlow.value = true
    }

    suspend fun logOut() {
        delay(1000)
        _sessionFlow.value = false
    }


}