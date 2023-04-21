package com.example.skeletonapp.ui.features.sliderInputFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.skeletonapp.databinding.FragmentSliderInputBinding
import com.example.skeletonapp.ui.shared.CurrencyTextWatcher
import com.example.skeletonapp.ui.shared.PercentTextWatcher
import com.example.skeletonapp.ui.shared.components.SliderInput
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SliderInputFragment : Fragment() {

    private var _binding: FragmentSliderInputBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SliderInputVewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSliderInputBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.sliderInput.apply {
            currencySymbol = "$"
            fieldFormat = SliderInput.InputFieldFormat.CURRENCY

            initializeSlider(
                10000.0,
                440000.0,
                40000.0,
                40000.0,
            )
//            initializeSlider(
//                0.0,
//                100.0,
//                5.0,
//                50.0,
//            )
            setInputHint("Enter decimal number")

            userInputCompletedCallback = {
                Log.d("TAG", ">>>> userInputCompletedCallback $it ")
            }
        }

        binding.currencyInput.addTextChangedListener(
            CurrencyTextWatcher(binding.currencyInput, "$", 2) {
                Log.d("TAG", ">>>>>>>> $it")
            }
        )

        binding.percentInput.addTextChangedListener(
            PercentTextWatcher(binding.percentInput) {
                Log.d("TAG", ">>>>>>>> $it")
            }
        )

        val dataSource = listOf("value1", "value2", "value3", "not_value1")

        binding.label.apply {
            text = dataSource.filter {
                it.startsWith("val")
            }.joinToString("\n")
                .takeIf {
                    !it.isNullOrEmpty()
                } ?: "Nothing to display"
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


