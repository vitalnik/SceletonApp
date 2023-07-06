package com.example.skeletonapp.ui.features.twowaybinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.core.extensions.observe
import com.example.skeletonapp.databinding.EditTextItemBinding
import kotlinx.coroutines.flow.MutableStateFlow

class RecyclerViewAdapter(
    private val lifecycleOwner: LifecycleOwner,
    var data: List<Pair<String, MutableStateFlow<String>>>,
    val onValueChange: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.ViewHolder {
        val binding = EditTextItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = data[position]

        with(holder) {

            binding.title = item.first

            item.second.observe(lifecycleOwner) {
                //Log.d("TAG", ">>> item.first => $it")

                onValueChange(position)
            }

            binding.value = item.second
        }
    }

    inner class ViewHolder(val binding: EditTextItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}