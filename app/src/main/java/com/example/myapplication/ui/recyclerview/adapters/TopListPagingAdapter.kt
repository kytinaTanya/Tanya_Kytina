package com.example.myapplication.ui.recyclerview.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BuildConfig
import com.example.myapplication.databinding.ItemMovieBinding
import com.example.myapplication.models.pojo.BaseItem
import com.example.myapplication.models.pojo.Film
import com.example.myapplication.models.pojo.Person
import com.example.myapplication.models.pojo.TV
import com.example.myapplication.utils.setImage

class TopListPagingAdapter : PagingDataAdapter<BaseItem, TopListPagingAdapter.ItemViewHolder>(ItemComparator) {

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
        getItem(position)?.let { item ->
            when (item) {
                is Film -> holder.bindMovie(item)
                is TV -> holder.bindTv(item)
                is Person -> holder.bindPerson(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        binding = ItemMovieBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemViewHolder(binding)
    }

    object ItemComparator: DiffUtil.ItemCallback<BaseItem>() {
        override fun areItemsTheSame(oldItem: BaseItem, newItem: BaseItem): Boolean {
            return when (oldItem) {
                is Film -> (oldItem as Film).id == (newItem as Film).id
                is TV -> (oldItem as TV).id == (newItem as TV).id
                is Person -> (oldItem as Person).id == (newItem as Person).id
                else -> oldItem == newItem
            }
        }

        override fun areContentsTheSame(oldItem: BaseItem, newItem: BaseItem): Boolean {
            return oldItem == newItem
        }

    }
}