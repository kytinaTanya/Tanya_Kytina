package com.example.myapplication.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BuildConfig
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemBackdropTitleBinding
import com.example.myapplication.databinding.ItemPersonBinding
import com.example.myapplication.databinding.ItemPosterBinding
import com.example.myapplication.models.movies.Film
import com.example.myapplication.models.movies.Movie
import com.example.myapplication.models.movies.Person
import com.example.myapplication.models.movies.TV
import com.example.myapplication.utils.setImage
import java.util.*

interface MovieClickListener {
    fun onOpenMovie(id: Long)
    fun onOpenTV(id: Long)
    fun onOpenPerson(id: Long)
}

class MovieRecyclerAdapter(private val listener: MovieClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {

    var mMoviesList: MutableList<Movie> = arrayListOf()

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

    class PersonViewHolder(val binding: ItemPersonBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(person: Person) {
            binding.profileImage.setImage(buildImageUrl(person))
            binding.name.text = person.name
        }

        fun buildImageUrl(person: Person): String {
            val imageUrl = BuildConfig.BASE_IMAGE_URL + person.profilePath
            Log.d("initImage", imageUrl)
            return imageUrl
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            R.layout.item_poster -> {
                val binding =
                    ItemPosterBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                binding.root.setOnClickListener(this)
                PosterViewHolder(binding)
            }
            R.layout.item_backdrop_title -> {
                val binding = ItemBackdropTitleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                binding.root.setOnClickListener(this)
                BackdropAndTitleViewHolder(binding)
            }
            R.layout.item_person -> {
                val binding =
                    ItemPersonBinding.inflate(LayoutInflater.from(parent.context),
                        parent,
                        false)
                binding.root.setOnClickListener(this)
                PersonViewHolder(binding)
            }
            else -> {
                val binding =
                    ItemPosterBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                binding.root.setOnClickListener(this)
                PosterViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tag = mMoviesList[position]
        when(holder) {
            is PosterViewHolder -> holder.bind(mMoviesList[position] as Film)
            is BackdropAndTitleViewHolder -> holder.bind(mMoviesList[position] as TV)
            is PersonViewHolder -> holder.bind(mMoviesList[position] as Person)
        }
    }

    override fun getItemCount() = mMoviesList.size

    override fun getItemViewType(position: Int): Int {
        return when(mMoviesList[position]) {
            is Film -> R.layout.item_poster
            is TV -> R.layout.item_backdrop_title
            is Person -> R.layout.item_person
        }
    }

    fun appendMovies(movies: List<Movie>) {
        mMoviesList.clear()
        mMoviesList.addAll(movies)
        notifyDataSetChanged()
    }

    override fun onClick(v: View?) {
        val movie = v?.tag as Movie
        when(movie) {
            is Film -> listener.onOpenMovie(movie.mid)
            is TV -> listener.onOpenTV(movie.id)
            is Person -> listener.onOpenPerson(movie.id)
        }
    }
}