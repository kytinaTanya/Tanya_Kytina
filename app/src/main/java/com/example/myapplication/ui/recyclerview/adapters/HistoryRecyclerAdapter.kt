package com.example.myapplication.ui.recyclerview.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.myapplication.BuildConfig
import com.example.myapplication.databinding.ItemMovieHistoryBinding
import com.example.myapplication.models.movies.Film
import com.example.myapplication.utils.setImage

class HistoryRecyclerAdapter : Adapter<HistoryRecyclerAdapter.HistoryViewHolder>() {

    private val filmList: MutableList<Film> = arrayListOf()

    class HistoryViewHolder(val binding: ItemMovieHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(film: Film) {
            binding.movieImage.setImage(BuildConfig.BASE_IMAGE_URL + film.backdropPath)
            binding.textView.text = film.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemMovieHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(filmList[position])
    }

    override fun getItemCount(): Int = filmList.size

    fun appendFilms(films: List<Film>) {
        filmList.clear()
        filmList.addAll(films)
        notifyDataSetChanged()
    }
}