package com.example.skeletonapp.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skeletonapp.MainViewModel
import com.example.skeletonapp.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SettingsViewModel by viewModels()

    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var recyclerViewAdapter: DataAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewAdapter = DataAdapter(emptyList())

        binding.recyclerView.adapter = recyclerViewAdapter

        lifecycleScope.launch {
            viewModel.displayData
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    recyclerViewAdapter.data = it
                    recyclerViewAdapter.notifyDataSetChanged()
                }
        }

//        lifecycleScope.launch {
//            mainViewModel.sessionFlow
//                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
//                .collect {
//                    binding.sessionStateText.text = if (it) "Logged In" else "Logged Out"
//                }
//        }

        lifecycleScope.launch {
            viewModel.sessionFlow
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    binding.sessionStateText.text = if (it) "Logged In" else "Logged Out"
                }
        }

        viewModel.loadData()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}