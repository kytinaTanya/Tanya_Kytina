package com.example.myapplication.ui.recyclerview.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.myapplication.BuildConfig
import com.example.myapplication.databinding.ItemMovieHistoryBinding
import com.example.myapplication.models.pojo.BaseItem
import com.example.myapplication.models.pojo.Episode
import com.example.myapplication.models.pojo.Film
import com.example.myapplication.models.pojo.TV
import com.example.myapplication.ui.recyclerview.listeners.MovieAndTvAndEpisodeListener
import com.example.myapplication.utils.setImage

class HistoryRecyclerAdapter(val listener: MovieAndTvAndEpisodeListener) : Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {

    private val filmList: MutableList<BaseItem> = arrayListOf()

    class HistoryViewHolder(val binding: ItemMovieHistoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(film: Film) {
            binding.movieImage.setImage(BuildConfig.BASE_BACKDROP_URL + film.backdropPath)
            binding.nameItem.text = film.title
            binding.generalGrade.text = "${film.rating}"
        }

        fun bind(tv: TV) {
            binding.movieImage.setImage(BuildConfig.BASE_BACKDROP_URL + tv.backdropPath)
            binding.nameItem.text = tv.name
            binding.generalGrade.text = "${tv.rating}"
        }

        fun bind(episode: Episode) {
            binding.movieImage.setImage(BuildConfig.BASE_STILL_URL + episode.stillPath)
            binding.nameItem.text = episode.name
            binding.generalGrade.text = "${episode.rating}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemMovieHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        binding.root.setOnClickListener(this)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tag = filmList[position]
        if(holder is HistoryViewHolder) {
            when(filmList[position]) {
                is Film -> holder.bind(filmList[position] as Film)
                is TV -> holder.bind(filmList[position] as TV)
                is Episode -> holder.bind(filmList[position] as Episode)
            }
        }
    }

    override fun getItemCount(): Int = filmList.size

    fun appendFilms(films: List<BaseItem>) {
        filmList.clear()
        filmList.addAll(films)
        notifyDataSetChanged()
    }

    override fun onClick(v: View?) {
        val movie = v?.tag as BaseItem
        when(movie) {
            is Film -> listener.onOpenMovie(movie.id)
            is TV -> listener.onOpenTV(movie.id)
            is Episode -> listener.onOpenEpisode(movie.showId, movie.seasonNum, movie.episodeNum)
        }
    }
}