package com.example.myapplication.ui.recyclerview.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.myapplication.BuildConfig
import com.example.myapplication.databinding.ItemMovieHistoryBinding
import com.example.myapplication.models.movies.Episode
import com.example.myapplication.models.movies.Film
import com.example.myapplication.models.movies.Movie
import com.example.myapplication.models.movies.TV
import com.example.myapplication.utils.setImage

class HistoryRecyclerAdapter(val listener: MovieClickListener) : Adapter<HistoryRecyclerAdapter.HistoryViewHolder>(), View.OnClickListener {

    private val filmList: MutableList<Movie> = arrayListOf()

    class HistoryViewHolder(val binding: ItemMovieHistoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(film: Film) {
            binding.movieImage.setImage(BuildConfig.BASE_BACKDROP_URL + film.backdropPath)
            binding.textView.text = film.title
            binding.generalGrade.text = "${film.rating}"
        }

        fun bind(tv: TV) {
            binding.movieImage.setImage(BuildConfig.BASE_BACKDROP_URL + tv.backdropPath)
            binding.textView.text = tv.name
            binding.generalGrade.text = "${tv.rating}"
        }

        fun bind(episode: Episode) {
            binding.movieImage.setImage(BuildConfig.BASE_STILL_URL + episode.stillPath)
            binding.textView.text = episode.name
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

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.itemView.tag = filmList[position]
        when(filmList[position]) {
            is Film -> holder.bind(filmList[position] as Film)
            is TV -> holder.bind(filmList[position] as TV)
            is Episode -> holder.bind(filmList[position] as Episode)
        }

    }

    override fun getItemCount(): Int = filmList.size

    fun appendFilms(films: List<Movie>) {
        filmList.clear()
        filmList.addAll(films)
        notifyDataSetChanged()
    }

    override fun onClick(v: View?) {
        val movie = v?.tag as Movie
        when(movie) {
            is Film -> listener.onOpenMovie(movie.id)
            is TV -> listener.onOpenTV(movie.id)
            is Episode -> listener.onOpenEpisode(movie.showId, movie.seasonNum, movie.episodeNum)
        }
    }
}