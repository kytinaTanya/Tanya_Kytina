package com.example.myapplication.ui.recyclerview.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BuildConfig
import com.example.myapplication.databinding.ItemMovieBinding
import com.example.myapplication.models.pojo.BaseItem
import com.example.myapplication.models.pojo.Film
import com.example.myapplication.models.pojo.TV
import com.example.myapplication.ui.recyclerview.itemcomparator.ItemComparator
import com.example.myapplication.ui.recyclerview.listeners.MovieClickListener
import com.example.myapplication.utils.setImage


class CollectionRecyclerAdapter(val listener: MovieClickListener):
    PagingDataAdapter<BaseItem, CollectionRecyclerAdapter.MovieViewHolder>(ItemComparator),
    View.OnClickListener {

    class MovieViewHolder(val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindFilm(movie: Film) {
            binding.movieImage.setImage(BuildConfig.BASE_POSTER_URL + movie.posterPath)
            binding.movieTitle.text = movie.title
            binding.movieAnnotation.text = movie.overview
            binding.yearOfMovie.text = movie.releaseYear()
        }
        fun bindTV(tv: TV) {
            binding.movieImage.setImage(BuildConfig.BASE_POSTER_URL + tv.posterPath)
            binding.movieTitle.text = tv.name
            binding.movieAnnotation.text = tv.overview
            binding.yearOfMovie.text = tv.rating.toString()
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.itemView.tag = getItem(position)
        when(getItem(position)) {
            is Film -> holder.bindFilm(getItem(position) as Film)
            is TV -> holder.bindTV(getItem(position) as TV)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.root.setOnClickListener(this)
        return MovieViewHolder(binding)
    }

    override fun onClick(v: View?) {
        val movie = v?.tag as BaseItem
        when(movie) {
            is Film -> listener.onOpenMovie(movie.id)
            is TV -> listener.onOpenTV(movie.id)
        }
    }
}