package com.example.featuremodule

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.featuremodule.databinding.ActivityFeatureBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FeatureActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFeatureBinding

    private val viewModel: FeatureViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFeatureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toggleSessionStateButton.setOnClickListener {
            viewModel.toggleSessionState()
        }

        lifecycleScope.launch {
            viewModel.sessionFlow
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {

                    binding.titleText.text = if (it) "Logged In" else "Logged Out"
                }
        }

    }
}