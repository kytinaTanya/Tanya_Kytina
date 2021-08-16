package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.LoadStateItemBinding

class LoadingStateAdapter(private val adapter: MoviePagingAdapter)
    : LoadStateAdapter<LoadStateViewHolder>() {

    private lateinit var binding: LoadStateItemBinding

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        binding = LoadStateItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return LoadStateViewHolder(binding) { adapter.retry() }
    }

}

class LoadStateViewHolder(val binding: LoadStateItemBinding, val retry : () -> Unit) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener {
            retry()
        }
    }

    fun bind(loadState: LoadState) {
        binding.errorMessage.isVisible = loadState is LoadState.Error
        binding.retryButton.isVisible = loadState is LoadState.Error
        binding.progressBar.isVisible = loadState is LoadState.Loading
    }
}