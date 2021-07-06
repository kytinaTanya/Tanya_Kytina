package com.example.myapplication.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BuildConfig
import com.example.myapplication.databinding.ItemMovieBinding
import com.example.myapplication.movies.Movie
import com.example.myapplication.utils.setImage
import java.util.*

class MovieRecyclerAdapter() : RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder>(){

    private var mMoviesList: MutableList<Movie> = LinkedList()
    private lateinit var binding: ItemMovieBinding

    class ViewHolder(binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root) {
        // работу с view реализовать через вью байндинг
        val title: TextView = binding.movieTitle
        val year: TextView = binding.yearOfMovie
        val annotation: TextView = binding.movieAnnotation
        val poster: ImageView = binding.movieImage

        fun bind(movie: Movie){
            title.text = movie.title
            year.text = movie.releaseYear()
            annotation.text = movie.overview
            poster.setImage(BuildConfig.BASE_IMAGE_URL + movie.posterPath)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mMoviesList[position])
    }

    override fun getItemCount() = mMoviesList.size

    fun appendMovies(movies: List<Movie>) {
        mMoviesList.clear()
        mMoviesList.addAll(movies)
        notifyDataSetChanged()
    }
}