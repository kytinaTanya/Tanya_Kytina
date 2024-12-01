package com.example.myapplication.ui.recyclerview.adapters

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BuildConfig
import com.example.myapplication.databinding.ItemSeasonBinding
import com.example.myapplication.models.pojo.BaseItem
import com.example.myapplication.models.pojo.Film
import com.example.myapplication.models.pojo.TV
import com.example.myapplication.ui.recyclerview.listeners.MovieAndTvClickListener
import com.example.myapplication.utils.setImage

class KnownForRecyclerAdapter(private val listener: MovieAndTvClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {

    var mMoviesList: MutableList<BaseItem> = arrayListOf()

    class KnownForViewHolder(val binding: ItemSeasonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tv: TV) {
            if (tv.posterPath == null || tv.posterPath == "") binding.poster.visibility = View.GONE
            binding.poster.setImage(BuildConfig.BASE_POSTER_URL + tv.posterPath)
            binding.seasonName.text = tv.name
            binding.numEpisodes.text = tv.overview
            binding.numEpisodes.maxLines = 5
            binding.numEpisodes.ellipsize = TextUtils.TruncateAt.END
        }

        fun bind(movie: Film) {
            if (movie.posterPath == null || movie.posterPath == "") binding.poster.visibility = View.GONE
            binding.poster.setImage(BuildConfig.BASE_POSTER_URL + movie.posterPath)
            binding.seasonName.text = movie.title
            binding.numEpisodes.text = movie.overview
            binding.numEpisodes.maxLines = 5
            binding.numEpisodes.ellipsize = TextUtils.TruncateAt.END
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KnownForViewHolder {
        val binding =
            ItemSeasonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.root.setOnClickListener(this)
        return KnownForViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = mMoviesList[position]
        holder.itemView.tag = item
        when (item) {
            is TV -> (holder as KnownForViewHolder).bind(item)
            is Film -> (holder as KnownForViewHolder).bind(item)
            else -> {}
        }
    }

    override fun getItemCount(): Int = mMoviesList.size

    fun appendMovies(baseItems: List<BaseItem>) {
        mMoviesList.clear()
        mMoviesList.addAll(baseItems)
        notifyDataSetChanged()
    }

    override fun onClick(v: View?) {
        val item = v?.tag
        when (item) {
            is TV -> listener.onOpenTV(item.id)
            is Film -> listener.onOpenMovie(item.id)
        }
    }
}
