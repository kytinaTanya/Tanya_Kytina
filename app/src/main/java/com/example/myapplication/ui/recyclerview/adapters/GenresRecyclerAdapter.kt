package com.example.myapplication.ui.recyclerview.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemGenreBinding
import com.example.myapplication.models.movies.BaseItem
import com.example.myapplication.models.movies.MovieGenres

class GenresRecyclerAdapter : RecyclerView.Adapter<GenresRecyclerAdapter.GenresViewHolder>() {

    private var genresList: MutableList<MovieGenres> = arrayListOf()

    class GenresViewHolder(val binding: ItemGenreBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: MovieGenres) {
            binding.name.text = genre.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        val binding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenresViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        holder.bind(genresList[position])
    }

    override fun getItemCount(): Int = genresList.size

    fun setGenres(list: List<MovieGenres>) {
        genresList.clear()
        genresList.addAll(list)
        notifyDataSetChanged()
    }
}