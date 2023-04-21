package com.example.skeletonapp.ui.features.composeFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.example.skeletonapp.ui.features.sliderInputFragment.SliderInputVewModel

@AndroidEntryPoint
class ComposeFragment : Fragment() {

    private val viewModel: ComposeVewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            // Dispose the Composition when viewLifecycleOwner is destroyed
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )

            setContent {

                var counter by rememberSaveable {
                    mutableStateOf(0)
                }

                val viewModelCounter by viewModel.counterFlow.collectAsState()

                MaterialTheme {
                    // In Compose world

                    Column(modifier = Modifier.padding(all = 16.dp)) {

                        Text("Hello Compose! $counter --- $viewModelCounter")

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(onClick = { counter++ }) {
                            Text("Click me!")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(onClick = {
                            viewModel.incrementCounter()
                        }) {
                            Text("Update view model")
                        }

                    }

                }
            }
        }

    }

}


