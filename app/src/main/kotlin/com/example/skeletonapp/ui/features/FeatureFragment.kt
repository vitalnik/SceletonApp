package com.example.skeletonapp.ui.features

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.skeletonapp.MainViewModel
import com.example.skeletonapp.R
import com.example.skeletonapp.databinding.FragmentFeatureBinding
import dagger.hilt.android.AndroidEntryPoint
import com.example.featuremodule.FeatureActivity

@AndroidEntryPoint
class FeatureFragment : Fragment() {

    private var _binding: FragmentFeatureBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FeatureVewModel by viewModels()

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFeatureBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.featureButton.setOnClickListener {
            val intent = Intent(requireContext(), FeatureActivity::class.java)
            startActivity(intent)
        }

        binding.sliderInputButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_feature_to_navigation_nested_fragment)
        }

        binding.composeFragmentButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_feature_to_compose_fragment)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}