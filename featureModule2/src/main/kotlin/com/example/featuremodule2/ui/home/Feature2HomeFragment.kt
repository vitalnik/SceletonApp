package com.example.featuremodule2.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.featuremodule2.R
import com.example.featuremodule2.databinding.FragmentFeature2HomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Feature2HomeFragment : Fragment() {

    companion object {
        fun newInstance() = Feature2HomeFragment()
    }

    private var _binding: FragmentFeature2HomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: Feature2HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFeature2HomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.regularFragmentButton.setOnClickListener {
            //findNavController().navigate(R.id.action_navigation_feature_to_navigation_nested_fragment)
        }

        binding.composeFragmentButton.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_feature2_home_to_fragment_feature2_compose)
        }


    }

}