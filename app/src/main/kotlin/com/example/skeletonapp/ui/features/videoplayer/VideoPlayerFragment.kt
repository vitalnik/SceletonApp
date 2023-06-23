package com.example.skeletonapp.ui.features.videoplayer

import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.webkit.WebViewAssetLoader
import androidx.webkit.WebViewClientCompat
import com.example.core.extensions.observe
import com.example.skeletonapp.databinding.FragmentVideoPlayerBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VideoPlayerFragment : Fragment() {

    private var _binding: FragmentVideoPlayerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: VideoPlayerVewModel by viewModels()


    private lateinit var videoPlayer1: ExoPlayer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentVideoPlayerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        videoPlayer1 = ExoPlayer.Builder(requireContext()).build()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.webView1) {

            settings.javaScriptEnabled = true
            settings.allowFileAccess = true
            settings.allowContentAccess = true

            webViewClient = object : WebViewClient() {
                override fun onReceivedSslError(
                    view: WebView?,
                    handler: SslErrorHandler?,
                    error: SslError?
                ) {
                    //super.onReceivedSslError(view, handler, error)
                    handler?.proceed()
                }
            }

            webChromeClient = object : WebChromeClient() {

            }
        }


        binding.videoPlayer1.player = videoPlayer1

        viewModel.helpVideo1Flow.observe(viewLifecycleOwner) {
            playVideo(videoPlayer1, it, autoPlay = true)
        }

        viewModel.helpWebViewFlow.observe(viewLifecycleOwner) {
            binding.webView1.loadUrl(it)
        }

        viewModel.initData()

    }

    private fun playVideo(videoPlayer: ExoPlayer, videoUrl: String, autoPlay: Boolean = true) {

        if (videoUrl.isEmpty()) {
            return
        }

        val extractorsFactory = DefaultExtractorsFactory().setConstantBitrateSeekingEnabled(true)

        val dataSourceFactory = DefaultHttpDataSource.Factory()
            .setAllowCrossProtocolRedirects(true)
            .setKeepPostFor302Redirects(true)
            .setConnectTimeoutMs(10000)

        val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory, extractorsFactory)
            .createMediaSource(MediaItem.fromUri(videoUrl))

        with(videoPlayer) {
            setMediaSource(mediaSource)
            prepare()
            if (autoPlay) {
                play()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        videoPlayer1.release()
    }

}
