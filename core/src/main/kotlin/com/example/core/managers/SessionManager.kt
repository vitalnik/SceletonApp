package com.example.core.managers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class SessionManager @Inject constructor() : CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Default + job

//    private val _sessionFlow = MutableSharedFlow<Boolean>(replay = 0)
//    val sessionFlow = _sessionFlow.asSharedFlow()

    private val _sessionFlow = MutableStateFlow<Boolean>(false)
    val sessionFlow = _sessionFlow.asStateFlow()

    suspend fun logIn() {
        delay(100)
        _sessionFlow.emit(true)
    }

    suspend fun logOut() {
        delay(100)
        _sessionFlow.emit(false)
    }

    suspend fun isLoggedIn() = sessionFlow.value


}