package com.example.myapplication.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BuildConfig
import com.example.myapplication.databinding.ItemMovieBinding
import com.example.myapplication.room.entity.Movie
import com.example.myapplication.utils.setImage
import java.util.*

interface MovieClickListener {
    fun onOpenMovie(id: Long)
}

class MovieRecyclerAdapter(private val listener: MovieClickListener) : RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder>(), View.OnClickListener {

    var mMoviesList: MutableList<Movie> = LinkedList()
    private lateinit var binding: ItemMovieBinding

    class ViewHolder(binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.movieTitle
        val year: TextView = binding.yearOfMovie
        val annotation: TextView = binding.movieAnnotation
        val poster: ImageView = binding.movieImage

        fun bind(movie: Movie){
            title.text = movie.title
            year.text = movie.releaseYear()
            annotation.text = movie.overview
            poster.setImage(buildImageUrl(movie))
        }

        private fun buildImageUrl(movie: Movie): String {
            val imageUrl = BuildConfig.BASE_IMAGE_URL + movie.posterPath
            Log.d("initImage", imageUrl)
            return imageUrl
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        binding.root.setOnClickListener(this)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tag = mMoviesList[position]
        holder.bind(mMoviesList[position])
    }

    override fun getItemCount() = mMoviesList.size

    fun appendMovies(movies: List<Movie>) {
        mMoviesList.clear()
        mMoviesList.addAll(movies)
        notifyDataSetChanged()
    }

    override fun onClick(v: View?) {
        val movie = v?.tag as Movie
        listener.onOpenMovie(movie.id)
    }
}