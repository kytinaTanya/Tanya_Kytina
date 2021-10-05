package com.example.myapplication.ui.recyclerview.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BuildConfig
import com.example.myapplication.databinding.ItemMovieBinding
import com.example.myapplication.models.movies.Film
import com.example.myapplication.models.movies.Movie
import com.example.myapplication.utils.setImage


class CollectionRecyclerAdapter: RecyclerView.Adapter<CollectionRecyclerAdapter.MovieViewHolder>() {

    private var movieList: MutableList<Film> = arrayListOf()

    class MovieViewHolder(val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Film) {
            binding.movieImage.setImage(BuildConfig.BASE_IMAGE_URL + movie.posterPath)
            binding.movieTitle.text = movie.title
            binding.movieAnnotation.text = movie.overview
            binding.yearOfMovie.text = movie.releaseYear()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun addMovies(movies: List<Film>) {
        movieList.clear()
        movieList.addAll(movies)
        notifyDataSetChanged()
    }
}