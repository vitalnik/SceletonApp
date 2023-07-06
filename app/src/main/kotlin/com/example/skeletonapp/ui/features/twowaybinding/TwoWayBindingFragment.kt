package com.example.skeletonapp.ui.features.twowaybinding

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.core.extensions.observe
import com.example.skeletonapp.databinding.FragmentTwoWayBindingBinding
import com.example.skeletonapp.ui.settings.DataAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TwoWayBindingFragment : Fragment() {

    private var _binding: FragmentTwoWayBindingBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TwoWayBindingViewModel by viewModels()

    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTwoWayBindingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        recyclerViewAdapter = RecyclerViewAdapter(viewLifecycleOwner, emptyList()) { position ->

            viewModel.checkValue(position)

        }

        recyclerViewAdapter.data = viewModel.fields

        binding.recyclerView.adapter = recyclerViewAdapter

        viewModel.textValueFlow.observe(viewLifecycleOwner) {

            Log.d("TAG", ">>> entered text: $it")

        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


