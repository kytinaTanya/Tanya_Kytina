package com.example.myapplication.ui.recyclerview.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BuildConfig
import com.example.myapplication.databinding.ItemMovieBinding
import com.example.myapplication.models.movies.Film
import com.example.myapplication.models.movies.Movie
import com.example.myapplication.models.movies.Person
import com.example.myapplication.models.movies.TV
import com.example.myapplication.utils.setImage


class CollectionRecyclerAdapter(val listener: MovieClickListener):
    RecyclerView.Adapter<CollectionRecyclerAdapter.MovieViewHolder>(),
    View.OnClickListener {

    private var movieList: MutableList<Movie> = arrayListOf()

    class MovieViewHolder(val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindFilm(movie: Film) {
            binding.movieImage.setImage(BuildConfig.BASE_IMAGE_URL + movie.posterPath)
            binding.movieTitle.text = movie.title
            binding.movieAnnotation.text = movie.overview
            binding.yearOfMovie.text = movie.releaseYear()
        }
        fun bindTV(tv: TV) {
            binding.movieImage.setImage(BuildConfig.BASE_IMAGE_URL + tv.posterPath)
            binding.movieTitle.text = tv.name
            binding.movieAnnotation.text = tv.overview
            binding.yearOfMovie.text = tv.rating.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.root.setOnClickListener(this)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.itemView.tag = movieList[position]
        when(movieList[position]) {
            is Film -> holder.bindFilm(movieList[position] as Film)
            is TV -> holder.bindTV(movieList[position] as TV)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun addMovies(movies: List<Movie>) {
        movieList.clear()
        movieList.addAll(movies)
        notifyDataSetChanged()
    }

    override fun onClick(v: View?) {
        val movie = v?.tag as Movie
        when(movie) {
            is Film -> listener.onOpenMovie(movie.id)
            is TV -> listener.onOpenTV(movie.id)
        }
    }
}