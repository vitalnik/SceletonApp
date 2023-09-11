package com.example.skeletonapp.ui.features

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.featuremodule2.Feature2Activity
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLException

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

        binding.featureButton2.setOnClickListener {
            val intent = Intent(requireContext(), Feature2Activity::class.java)
            startActivity(intent)
        }

        binding.sliderInputButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_feature_to_navigation_nested_fragment)
        }

        binding.composeFragmentButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_feature_to_compose_fragment)
        }

        binding.videoPlayerButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_fragment_feature_to_video_player_fragment)
        }

        binding.twoWayBindingButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_fragment_feature_to_two_way_binding_fragment)
        }

        binding.baseViewModelButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_fragment_feature_to_base_viewmodel_fragment)
        }

        binding.networkingButton.setOnClickListener {
            viewModel.testNetworking()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}