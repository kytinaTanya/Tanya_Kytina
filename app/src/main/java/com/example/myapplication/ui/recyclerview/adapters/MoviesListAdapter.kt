package com.example.myapplication.ui.recyclerview.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BuildConfig
import com.example.myapplication.databinding.ItemBackdropTitleFullWightBinding
import com.example.myapplication.models.pojo.Film
import com.example.myapplication.ui.recyclerview.listeners.MovieClickListener
import com.example.myapplication.utils.setImage

class MoviesListAdapter(private val listener: MovieClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {

    private val dataList: MutableList<Film> = mutableListOf()

    class MoviesOfCollectionViewHolder(val binding: ItemBackdropTitleFullWightBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Film) {
            binding.backdropImage.setImage(buildImageUrl(movie))
            binding.title.text = movie.title
        }

        private fun buildImageUrl(movie: Film): String {
            val imageUrl = BuildConfig.BASE_BACKDROP_URL + movie.backdropPath
            Log.d("initImage", imageUrl)
            return imageUrl
        }
    }

    fun addMovies(movies: List<Film>) {
        dataList.clear()
        dataList.addAll(movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesOfCollectionViewHolder {
        val binding = ItemBackdropTitleFullWightBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)
        binding.root.setOnClickListener(this)
        return MoviesOfCollectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tag = dataList[position]
        (holder as MoviesOfCollectionViewHolder).bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    override fun onClick(p0: View?) {
        val movie = p0?.tag as Film
        listener.onOpenMovie(movie.id)
    }
}