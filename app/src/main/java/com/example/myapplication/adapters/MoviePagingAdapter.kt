package com.example.myapplication.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BuildConfig
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.ItemMovieBinding
import com.example.myapplication.movies.Movie
import com.example.myapplication.utils.setImage

class MoviePagingAdapter : PagingDataAdapter<Movie, MoviePagingAdapter.MovieViewHolder>(MovieComparator) {

    private lateinit var binding: ItemMovieBinding

    class MovieViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.movieTitle.text = movie.title
            binding.movieAnnotation.text = movie.overview
            binding.yearOfMovie.text = movie.releaseYear()
            binding.movieImage.setImage(buildImageUrl(movie))
        }

        private fun buildImageUrl(movie: Movie): String {
            val imageUrl = BuildConfig.BASE_IMAGE_URL + movie.posterPath
            Log.d("initImage", imageUrl)
            return imageUrl
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        binding = ItemMovieBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MovieViewHolder(binding)
    }

    object MovieComparator: DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }
}