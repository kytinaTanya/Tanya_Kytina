package com.example.myapplication.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BuildConfig
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemBackdropTitleBinding
import com.example.myapplication.databinding.ItemPosterBinding
import com.example.myapplication.models.Film
import com.example.myapplication.models.Movie
import com.example.myapplication.models.TV
import com.example.myapplication.utils.setImage
import java.util.*

interface MovieClickListener {
    fun onOpenMovie(id: Long)
}

class MovieRecyclerAdapter(private val listener: MovieClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {

    var mMoviesList: MutableList<Movie> = LinkedList()

    class PosterViewHolder(val binding: ItemPosterBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Film){
            binding.posterImage.setImage(buildImageUrl(movie))
        }

        private fun buildImageUrl(movie: Film): String {
            val imageUrl = BuildConfig.BASE_IMAGE_URL + movie.posterPath
            Log.d("initImage", imageUrl)
            return imageUrl
        }
    }

    class BackdropAndTitleViewHolder(val binding: ItemBackdropTitleBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(tv: TV){
            binding.backdropImage.setImage(buildImageUrl(tv))
            binding.title.text = tv.name
        }

        fun buildImageUrl(tv: TV): String {
            val imageUrl = BuildConfig.BASE_IMAGE_URL + tv.backdropPath
            Log.d("initImage", imageUrl)
            return imageUrl
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == R.layout.item_poster) {
            val binding = ItemPosterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            binding.root.setOnClickListener(this)
            PosterViewHolder(binding)
        } else {
            val binding = ItemBackdropTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            binding.root.setOnClickListener(this)
            BackdropAndTitleViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tag = mMoviesList[position]
        when(holder) {
            is PosterViewHolder -> holder.bind(mMoviesList[position] as Film)
            is BackdropAndTitleViewHolder -> holder.bind(mMoviesList[position] as TV)
        }
    }

    override fun getItemCount() = mMoviesList.size

    override fun getItemViewType(position: Int): Int {
        return when(mMoviesList[position]) {
            is Film -> R.layout.item_poster
            is TV -> R.layout.item_backdrop_title
            else -> R.layout.item_poster
        }
    }

    fun appendMovies(movies: List<Movie>) {
        mMoviesList.clear()
        mMoviesList.addAll(movies)
        notifyDataSetChanged()
    }

    override fun onClick(v: View?) {
        val movie = v?.tag as Movie
        listener.onOpenMovie(movie.mid)
    }
}