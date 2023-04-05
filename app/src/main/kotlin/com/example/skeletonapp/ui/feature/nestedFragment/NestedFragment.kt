package com.example.skeletonapp.ui.feature.nestedFragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.skeletonapp.databinding.FragmentNestedBinding
import com.google.android.material.slider.Slider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.ceil
import kotlin.math.floor


@AndroidEntryPoint
class NestedFragment : Fragment() {

    private var _binding: FragmentNestedBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NestedVewModel by viewModels()

    private val textWatcher: TextWatcher = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            Log.d("TAG", ">>> beforeTextChanged: $s")

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            Log.d("TAG", ">>> onTextChanged $s")

        }

        override fun afterTextChanged(s: Editable) {

            Log.d("TAG", ">>> afterTextChanged $s")

            val currentValue = if (s.isEmpty()) 0.0 else s.toString().toDouble()

            val normalizedValue = normalizeNumber(currentValue)

            binding.slider.value = normalizedValue.toFloat()
            binding.normalizedNumber.text = normalizedValue.toString()

            viewModel.updateTextValue(normalizedValue.toString())
        }
    }

    private fun normalizeNumber(originalNumber: Double): Double {

        if (originalNumber < viewModel.sliderMinValue) {
            return viewModel.sliderMinValue
        } else if (originalNumber > viewModel.sliderMaxValue) {
            return viewModel.sliderMaxValue
        }

        val halfOfIncrement = viewModel.sliderIncrement / 2
        val floorResult = floor(originalNumber / viewModel.sliderIncrement)
        val roundingThreshold = (floorResult * viewModel.sliderIncrement) + halfOfIncrement

        return if (originalNumber >= roundingThreshold)
            ceil(originalNumber / viewModel.sliderIncrement) * viewModel.sliderIncrement
        else
            floor(originalNumber / viewModel.sliderIncrement) * viewModel.sliderIncrement
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNestedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        lifecycleScope.launch {
            viewModel.textValueFlow
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    binding.textInput.setText(it)
                }
        }

        binding.slider.setMinAndMaxValues(
            viewModel.sliderMinValue,
            viewModel.sliderMaxValue,
            viewModel.sliderIncrement,
            viewModel.sliderValue
        )

        binding.slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
                //no-op
            }

            override fun onStopTrackingTouch(slider: Slider) {

            }
        })

        binding.slider.addOnChangeListener { slider, value, fromUser ->
            if (fromUser) {
                binding.textInput.setText(value.toDouble().toString())
            }
        }

        binding.textInput.addTextChangedListener(textWatcher)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}