package com.example.myapplication.ui.recyclerview.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BuildConfig
import com.example.myapplication.databinding.ItemMovieBinding
import com.example.myapplication.models.pojo.BaseItem
import com.example.myapplication.models.pojo.Film
import com.example.myapplication.models.pojo.TV
import com.example.myapplication.ui.recyclerview.listeners.MovieClickListener
import com.example.myapplication.utils.setImage


class CollectionRecyclerAdapter(val listener: MovieClickListener):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    View.OnClickListener {

    private var baseItemList: MutableList<BaseItem> = arrayListOf()

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.root.setOnClickListener(this)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tag = baseItemList[position]
        when(baseItemList[position]) {
            is Film -> (holder as MovieViewHolder).bindFilm(baseItemList[position] as Film)
            is TV -> (holder as MovieViewHolder).bindTV(baseItemList[position] as TV)
        }
    }

    override fun getItemCount(): Int {
        return baseItemList.size
    }

    fun addMovies(baseItems: List<BaseItem>) {
        baseItemList.clear()
        baseItemList.addAll(baseItems)
        notifyDataSetChanged()
    }

    override fun onClick(v: View?) {
        val movie = v?.tag as BaseItem
        when(movie) {
            is Film -> listener.onOpenMovie(movie.id)
            is TV -> listener.onOpenTV(movie.id)
        }
    }
}