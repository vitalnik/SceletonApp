package com.example.core.managers

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor() {

    private val _sessionFlow = MutableStateFlow(false)
    val sessionFlow = _sessionFlow.asStateFlow()

    suspend fun logIn() {
        delay(100)
        _sessionFlow.value = true
    }

    suspend fun logOut() {
        delay(100)
        _sessionFlow.value = false
    }

    fun isLoggedIn() = sessionFlow.value

}