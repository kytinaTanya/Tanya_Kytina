package com.example.myapplication.ui.recyclerview.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.*
import com.example.myapplication.models.lists.*

interface ListClickListener {
    fun openList(list: MovieList)
}

class ListsRecyclerAdapter(val listener: ListClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {

    var listOfLists: MutableList<MovieList> = arrayListOf()

    class CreatedListViewHolder(val binding: ItemCreatedListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(list: CreatedList) {
            binding.title.text = list.name
            binding.count.text = "${list.itemCount} фильмов"
        }
    }

    class FavoriteMovieViewHolder(val binding: ItemFavoriteMoviesBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(list: FavouriteMovieList) {
            binding.title.text = "Любимые фильмы"
            binding.count.text = "${list.totalResults} фильмов"
        }
    }

    class FavoriteTvViewHolder(val binding: ItemFavoriteTvsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(list: FavouriteTVList) {
            binding.title.text = "Любимые сериалы"
            binding.count.text = "${list.totalResults} сериалов"
        }
    }

    class MovieWatchlistViewHolder(val binding: ItemMovieWatchlistBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(list: MovieWatchList) {
            binding.title.text = "Вотчлист фильмов"
            binding.count.text = "${list.totalResults} фильмов"
        }
    }

    class TvWatchlistViewHolder(val binding: ItemTvWatchlistBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(list: TVWatchList) {
            binding.title.text = "Вотчлист сериалов"
            binding.count.text = "${list.totalResults} сериалов"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            R.layout.item_created_list -> {
                val binding =
                    ItemCreatedListBinding.inflate(LayoutInflater.from(parent.context),
                    parent,
                    false)
                binding.root.setOnClickListener(this)
                CreatedListViewHolder(binding)
            }
            R.layout.item_favorite_movies -> {
                val binding =
                    ItemFavoriteMoviesBinding.inflate(LayoutInflater.from(parent.context),
                    parent,
                    false)
                binding.root.setOnClickListener(this)
                FavoriteMovieViewHolder(binding)
            }
            R.layout.item_favorite_tvs -> {
                val binding =
                    ItemFavoriteTvsBinding.inflate(LayoutInflater.from(parent.context),
                    parent,
                    false)
                binding.root.setOnClickListener(this)
                FavoriteTvViewHolder(binding)
            }
            R.layout.item_movie_watchlist -> {
                val binding =
                    ItemMovieWatchlistBinding.inflate(LayoutInflater.from(parent.context),
                        parent,
                        false)
                binding.root.setOnClickListener(this)
                MovieWatchlistViewHolder(binding)
            }
            else -> {
                val binding =
                    ItemTvWatchlistBinding.inflate(LayoutInflater.from(parent.context),
                        parent,
                        false)
                binding.root.setOnClickListener(this)
                TvWatchlistViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tag = listOfLists[position]
        when(holder) {
            is CreatedListViewHolder -> holder.bind(listOfLists[position] as CreatedList)
            is FavoriteMovieViewHolder -> holder.bind(listOfLists[position] as FavouriteMovieList)
            is FavoriteTvViewHolder -> holder.bind(listOfLists[position] as FavouriteTVList)
            is MovieWatchlistViewHolder -> holder.bind(listOfLists[position] as MovieWatchList)
            is TvWatchlistViewHolder -> holder.bind(listOfLists[position] as TVWatchList)
        }
    }

    override fun getItemCount() = listOfLists.size

    override fun getItemViewType(position: Int): Int {
        return when(listOfLists[position]) {
            is CreatedList -> R.layout.item_created_list
            is FavouriteMovieList -> R.layout.item_favorite_movies
            is FavouriteTVList -> R.layout.item_favorite_tvs
            is MovieWatchList -> R.layout.item_movie_watchlist
            is TVWatchList -> R.layout.item_tv_watchlist
        }
    }

    fun addLists(list: List<MovieList>) {
        listOfLists.clear()
        listOfLists.addAll(list)
        notifyDataSetChanged()
    }

    fun addList(list: MovieList) {
        listOfLists.add(list)
        notifyDataSetChanged()
    }

    override fun onClick(v: View?) {
        val list = v?.tag as MovieList
        listener.openList(list)
    }
}