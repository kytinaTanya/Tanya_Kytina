package com.example.myapplication.ui.recyclerview.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BuildConfig
import com.example.myapplication.databinding.ItemMovieBinding
import com.example.myapplication.models.pojo.BaseItem
import com.example.myapplication.models.pojo.Film
import com.example.myapplication.models.pojo.Person
import com.example.myapplication.models.pojo.TV
import com.example.myapplication.ui.recyclerview.itemcomparator.ItemComparator
import com.example.myapplication.ui.recyclerview.listeners.MovieAndTvAndPersonListener
import com.example.myapplication.utils.setImage

class TopListPagingAdapter(private val listener: MovieAndTvAndPersonListener) :
    PagingDataAdapter<BaseItem, TopListPagingAdapter.ItemViewHolder>(ItemComparator),
    View.OnClickListener {

    private lateinit var binding: ItemMovieBinding

    class ItemViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindMovie(movie: Film) {
            binding.movieTitle.text = movie.title
            binding.movieAnnotation.text = movie.overview
            binding.yearOfMovie.text = movie.releaseYear()
            binding.movieImage.setImage(buildImageUrl(movie.posterPath))
        }

        fun bindTv(tv: TV) {
            binding.movieTitle.text = tv.name
            binding.movieAnnotation.text = tv.overview
            binding.yearOfMovie.text = tv.rating.toString()
            binding.movieImage.setImage(buildImageUrl(tv.posterPath))
        }

        fun bindPerson(person: Person) {
            binding.movieTitle.text = person.name
            binding.movieAnnotation.text = person.biography
            binding.yearOfMovie.text = person.popularity.toString()
            binding.movieImage.setImage(buildImageUrl(person.profilePath ?: ""))
        }

        private fun buildImageUrl(path: String): String {
            val imageUrl = BuildConfig.BASE_POSTER_URL + path
            Log.d("initImage", imageUrl)
            return imageUrl
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.tag = getItem(position)
        getItem(position)?.let { item ->
            when (item) {
                is Film -> holder.bindMovie(item)
                is TV -> holder.bindTv(item)
                is Person -> holder.bindPerson(item)
                else -> {}
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        binding = ItemMovieBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        binding.root.setOnClickListener(this)
        return ItemViewHolder(binding)
    }

    override fun onClick(view: View?) {
        val movie = view?.tag as BaseItem
        when (movie) {
            is Film -> listener.onOpenMovie(movie.id)
            is TV -> listener.onOpenTV(movie.id)
            is Person -> listener.onOpenPerson(movie.id)
            else -> {}
        }
    }
}