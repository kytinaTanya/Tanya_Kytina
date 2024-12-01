package com.example.myapplication.ui.recyclerview.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BuildConfig
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemHeaderBinding
import com.example.myapplication.databinding.ItemMovieBinding
import com.example.myapplication.models.pojo.BaseItem
import com.example.myapplication.models.pojo.Film
import com.example.myapplication.models.pojo.HeaderItem
import com.example.myapplication.models.pojo.TV
import com.example.myapplication.ui.recyclerview.listeners.MovieAndTvClickListener
import com.example.myapplication.utils.setImage

class SearchResultRecyclerAdapter(val listener: MovieAndTvClickListener):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {

    private var baseItemList: MutableList<BaseItem> = arrayListOf()

    class SearchItemViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
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

    class HeaderViewHolder(val binding: ItemHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindHeader(headerItem: HeaderItem) {
            binding.title.text = headerItem.text
        }

        companion object {
            const val TV = "Сериалы"
            const val MOVIE = "Фильмы"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_header -> {
                val binding = ItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return HeaderViewHolder(binding)
            }
            else -> {
                val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                binding.root.setOnClickListener(this)
                return SearchItemViewHolder(binding)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tag = baseItemList[position]
        when (holder) {
            is SearchItemViewHolder -> {
                if (baseItemList[position] is Film) holder.bindFilm(baseItemList[position] as Film) else holder.bindTV(baseItemList[position] as TV)
            }
            is HeaderViewHolder -> {
                holder.bindHeader(baseItemList[position] as HeaderItem)
            }
        }
    }

    override fun getItemCount(): Int = baseItemList.size

    override fun getItemViewType(position: Int): Int {
        return when (baseItemList[position]) {
            is HeaderItem -> R.layout.item_header
            else -> R.layout.item_movie
        }
    }

    override fun onClick(v: View?) {
        val movie = v?.tag as BaseItem
        when(movie) {
            is Film -> listener.onOpenMovie(movie.id)
            is TV -> listener.onOpenTV(movie.id)
            else -> {}
        }
    }

    fun addMovies(movies: List<BaseItem>, type: String) {
        if (movies.isNotEmpty()) {
            baseItemList.add(HeaderItem(type))
            baseItemList.addAll(movies)
            notifyDataSetChanged()
        }
    }
}
