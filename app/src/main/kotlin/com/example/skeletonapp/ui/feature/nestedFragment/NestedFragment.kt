package com.example.skeletonapp.ui.feature.nestedFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.skeletonapp.databinding.FragmentNestedBinding
import com.example.skeletonapp.ui.shared.components.SliderInput
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NestedFragment : Fragment() {

    private var _binding: FragmentNestedBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NestedVewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNestedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.sliderInput.apply {
            currencySymbol = "$"
            fieldFormat = SliderInput.InputFieldFormat.CURRENCY

            initialize(
                10000.0,
                440000.0,
                40000.0,
                40000.0,
            )
            setInputHint("Enter decimal number")

            userInputStoppedCallback = {

            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}