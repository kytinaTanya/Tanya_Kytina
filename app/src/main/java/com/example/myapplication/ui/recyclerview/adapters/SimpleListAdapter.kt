package com.example.myapplication.ui.recyclerview.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemLogoBinding

class SimpleListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val stringList: MutableList<String> = mutableListOf()

    class SimpleViewHolder(val binding: ItemLogoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String) {
            binding.logo.text = name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val binding = ItemLogoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SimpleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SimpleViewHolder).bind(stringList[position])
    }

    override fun getItemCount(): Int = stringList.size

    fun addStrings(data: List<String>) {
        stringList.addAll(data)
        notifyDataSetChanged()
    }
}