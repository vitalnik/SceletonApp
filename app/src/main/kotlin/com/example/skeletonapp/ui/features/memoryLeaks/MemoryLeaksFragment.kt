package com.example.skeletonapp.ui.features.memoryLeaks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.core.extensions.observe
import com.example.skeletonapp.databinding.FragmentMemoryLeaksBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MemoryLeaksFragment : Fragment() {

    private var _binding: FragmentMemoryLeaksBinding? = null
    private val binding get() = _binding!!

//    @Inject
//    lateinit var clickListenerGenerator: ClickListenerGenerator

    private val itemClickListener: ItemClickListener by lazy {
        ClickListenerGenerator(requireActivity() as? AppCompatActivity).createListener()
    }

    private val viewModel: MemoryLeaksVewModel by viewModels()

    private lateinit var adapter: MemoryLeaksAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Log.d("TAG", ">>> MemoryLeaksFragment created -- Context: $context")

        _binding = FragmentMemoryLeaksBinding.inflate(inflater, container, false)
        val root: View = binding.root

        adapter = MemoryLeaksAdapter(
            data = mutableListOf(),
            clickListener = itemClickListener
        )

        binding.recyclerView.adapter = adapter

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.list.observe(lifecycleOwner = viewLifecycleOwner) {
            adapter.updateData(it)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        Log.d("TAG", ">>> MemoryLeaksFragment destroyed -- Context: $context")

    }


}