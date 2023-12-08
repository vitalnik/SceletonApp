package com.example.skeletonapp.ui.features.memoryLeaks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skeletonapp.databinding.ItemMemoryLeakBinding

class MemoryLeaksAdapter(
    var data: MutableList<String>,
    val clickListener: ItemClickListener,
) : RecyclerView.Adapter<MemoryLeaksAdapter.ViewHolder>() {

    fun updateData(newList: List<String>) {
        data.clear()
        data.addAll(newList)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MemoryLeaksAdapter.ViewHolder {
        val binding = ItemMemoryLeakBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = data[position]

        with(holder) {

            binding.itemText.text = item

            binding.itemButton.setOnClickListener {
                clickListener.onClick("Item at $position clicked")
            }

        }
    }

    inner class ViewHolder(val binding: ItemMemoryLeakBinding) :
        RecyclerView.ViewHolder(binding.root)
}