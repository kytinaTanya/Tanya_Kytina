package com.example.myapplication.ui.recyclerview.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BuildConfig
import com.example.myapplication.databinding.ItemEpisodesBinding
import com.example.myapplication.models.pojo.Episode
import com.example.myapplication.ui.recyclerview.listeners.EpisodeClickListener
import com.example.myapplication.utils.setImage

class EpisodeRecyclerAdapter(private val listener: EpisodeClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {

    var mMoviesList: MutableList<Episode> = arrayListOf()

    class EpisodeViewHolder(val binding: ItemEpisodesBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(episode: Episode) {
            binding.movieImage.setImage(BuildConfig.BASE_STILL_URL + episode.stillPath)
            binding.nameEpisode.text = episode.name
            binding.rating.text = "Рейтинг: " + episode.rating.toString()
            binding.overview.text = episode.overview
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val binding = ItemEpisodesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.root.setOnClickListener(this)
        return EpisodeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tag = mMoviesList[position]
        (holder as EpisodeViewHolder).bind(mMoviesList[position])
    }

    override fun getItemCount(): Int = mMoviesList.size

    fun appendMovies(baseItems: List<Episode>) {
        mMoviesList.clear()
        mMoviesList.addAll(baseItems)
        notifyDataSetChanged()
    }

    override fun onClick(v: View?) {
        val movie = v?.tag as Episode
        listener.onOpenEpisode(movie.showId, movie.seasonNum, movie.episodeNum)
    }
}
