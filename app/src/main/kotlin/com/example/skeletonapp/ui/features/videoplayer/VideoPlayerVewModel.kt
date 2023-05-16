package com.example.skeletonapp.ui.features.videoplayer

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class VideoPlayerVewModel : ViewModel() {

    private val _helpVideo1Flow = MutableStateFlow("")
    val helpVideo1Flow = _helpVideo1Flow.asStateFlow()

    private val _helpWebViewFlow = MutableStateFlow("")
    val helpWebViewFlow = _helpWebViewFlow.asStateFlow()

    fun initData() {

        _helpVideo1Flow.value =
            "https://d3lgvydj16g1oi.cloudfront.net/f70419b977eae5f5b346ec63262869e7/720p.mp4?1684183332"

        _helpWebViewFlow.value =
            "https://flimp.me/decision_support-Insured_Members?em=Y"

    }


}