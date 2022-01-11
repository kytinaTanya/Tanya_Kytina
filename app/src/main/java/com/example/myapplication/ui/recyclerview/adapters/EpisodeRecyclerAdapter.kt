package com.example.myapplication.ui.recyclerview.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BuildConfig
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemBackdropTitleFullWightBinding
import com.example.myapplication.databinding.ItemEpisodesBinding
import com.example.myapplication.models.pojo.BaseItem
import com.example.myapplication.models.pojo.Episode
import com.example.myapplication.models.pojo.Film
import com.example.myapplication.utils.setImage

class EpisodeRecyclerAdapter(private val listener: MovieClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {

    var mMoviesList: MutableList<BaseItem> = arrayListOf()

    class MoviesOfCollectionViewHolder(val binding: ItemBackdropTitleFullWightBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Film){
            binding.backdropImage.setImage(buildImageUrl(movie))
            binding.title.text = movie.title
        }

        fun buildImageUrl(movie: Film): String {
            val imageUrl = BuildConfig.BASE_BACKDROP_URL + movie.backdropPath
            Log.d("initImage", imageUrl)
            return imageUrl
        }
    }

    class EpisodeViewHolder(val binding: ItemEpisodesBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(episode: Episode) {
            binding.movieImage.setImage(BuildConfig.BASE_STILL_URL + episode.stillPath)
            binding.nameEpisode.text = episode.name
            binding.rating.text = "Рейтинг: " + episode.rating.toString()
            binding.overview.text = episode.overview
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_backdrop_title_full_wight -> {
                val binding = ItemBackdropTitleFullWightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                binding.root.setOnClickListener(this)
                MoviesOfCollectionViewHolder(binding)
            }
            else -> {
                val binding = ItemEpisodesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                binding.root.setOnClickListener(this)
                EpisodeViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tag = mMoviesList[position]
        when (holder) {
            is MoviesOfCollectionViewHolder -> holder.bind(mMoviesList[position] as Film)
            is EpisodeViewHolder -> holder.bind(mMoviesList[position] as Episode)
        }
    }

    override fun getItemCount(): Int = mMoviesList.size

    override fun getItemViewType(position: Int): Int {
        return when (mMoviesList[position]) {
            is Film -> R.layout.item_backdrop_title_full_wight
            else -> R.layout.item_episodes
        }
    }

    fun appendMovies(baseItems: List<BaseItem>) {
        mMoviesList.clear()
        mMoviesList.addAll(baseItems)
        notifyDataSetChanged()
    }

    override fun onClick(v: View?) {
        val movie = v?.tag as BaseItem
        when(movie) {
            is Film -> listener.onOpenMovie(movie.id)
            is Episode -> listener.onOpenEpisode(movie.showId, movie.seasonNum, movie.episodeNum)
        }
    }
}
