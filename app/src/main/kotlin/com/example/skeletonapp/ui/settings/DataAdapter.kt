package com.example.skeletonapp.ui.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skeletonapp.databinding.SettingsItemBinding

class DataAdapter(
    var data: List<String>
) : RecyclerView.Adapter<DataAdapter.SettingsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataAdapter.SettingsViewHolder {
        val binding = SettingsItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return SettingsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {

        val item = data[position]

        with(holder) {
            binding.itemText.text = item
        }

    }

    inner class SettingsViewHolder(val binding: SettingsItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}