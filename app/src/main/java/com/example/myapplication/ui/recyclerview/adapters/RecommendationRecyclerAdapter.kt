package com.example.myapplication.ui.recyclerview.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BuildConfig
import com.example.myapplication.databinding.ItemBackdropTitleBinding
import com.example.myapplication.models.pojo.BaseItem
import com.example.myapplication.models.pojo.Film
import com.example.myapplication.models.pojo.TV
import com.example.myapplication.utils.setImage

class RecommendationRecyclerAdapter(val listener: MovieClickListener):
    RecyclerView.Adapter<RecommendationRecyclerAdapter.RecommendationViewHolder>(), View.OnClickListener {

    private val recommendations: MutableList<BaseItem> = arrayListOf()

    class RecommendationViewHolder(val binding: ItemBackdropTitleBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Film) {
            binding.title.text = movie.title
            binding.backdropImage.setImage(BuildConfig.BASE_BACKDROP_URL + movie.backdropPath)
        }

        fun bind(tv: TV) {
            binding.title.text = tv.name
            binding.backdropImage.setImage(BuildConfig.BASE_BACKDROP_URL + tv.backdropPath)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationViewHolder {
        val binding = ItemBackdropTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.root.setOnClickListener(this)
        return RecommendationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendationViewHolder, position: Int) {
        holder.itemView.tag = recommendations[position]
        when(recommendations[position]) {
            is Film -> holder.bind(recommendations[position] as Film)
            is TV -> holder.bind(recommendations[position] as TV)
        }
    }

    override fun getItemCount(): Int = recommendations.size

    fun setItems(items: List<BaseItem>) {
        recommendations.clear()
        recommendations.addAll(items)
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