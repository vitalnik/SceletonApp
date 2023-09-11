package com.example.skeletonapp.ui.features.baseviewmodel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.skeletonapp.databinding.FragmentBaseViewModelBinding
import com.example.skeletonapp.databinding.FragmentSliderInputBinding
import com.example.skeletonapp.ui.shared.CurrencyTextWatcher
import com.example.skeletonapp.ui.shared.PercentTextWatcher
import com.example.skeletonapp.ui.shared.components.SliderInput
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BaseViewModelFragment : Fragment() {

    private var _binding: FragmentBaseViewModelBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ChildVewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBaseViewModelBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.label.apply {
            text = viewModel.getParentData() + viewModel.childData
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


