package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.movies.Movie
import com.squareup.picasso.Picasso
import java.util.*

class MovieRecyclerAdapter() : RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder>(){

    private var mMoviesList: MutableList<Movie> = LinkedList()

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        // работу с view реализовать через вью байндинг
        val title: TextView = view.findViewById(R.id.movieTitle)
        val year: TextView = view.findViewById(R.id.yearOfMovie)
        val annotation: TextView = view.findViewById(R.id.movieAnnotation)
        val poster: ImageView = view.findViewById(R.id.movieImage)

        fun bind(movie: Movie){
            title.text = movie.title
            year.text = movie.releaseYear()
            annotation.text = movie.overview

            // работу с Picasso вынести в функцию расширения или в функцию высшего порядка
            Picasso.get()
                // url подставлять из константы сформированной в gradle
                .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .error(R.drawable.poster_placeholder)
                .into(poster)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_movie, parent, false)

        return ViewHolder(view)
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