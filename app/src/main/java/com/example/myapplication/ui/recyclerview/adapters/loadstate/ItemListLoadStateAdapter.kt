package com.example.myapplication.ui.recyclerview.adapters.loadstate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.LoadStateItemBinding

class ItemListLoadStateAdapter(
    private val retry: () -> Unit,
) : LoadStateAdapter<ItemListLoadStateAdapter.LoadStateViewHolder>() {

    class LoadStateViewHolder(private val binding: LoadStateItemBinding, retry: () -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.errorButton.setOnClickListener { retry() }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                progress.isVisible = loadState is LoadState.Loading
                errorButton.isVisible = loadState is LoadState.Error
                errorText.isVisible = loadState is LoadState.Error
            }
        }
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        LoadStateViewHolder(
            LoadStateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            retry
        )
}