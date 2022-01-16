package com.example.myapplication.ui.recyclerview.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemVideoBinding
import com.example.myapplication.models.pojo.TrailerResult
import com.example.myapplication.utils.setImage

class VideoRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val videos: MutableList<TrailerResult> = arrayListOf()

    class VideoViewHolder(private val binding: ItemVideoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(video: TrailerResult) {
            binding.videoPlayer.setImage("https://i.ytimg.com/vi/${video.key}/maxresdefault.jpg")
            binding.root.setOnClickListener {
                binding.web.loadUrl("https://www.youtube.com/watch?v=${video.key}")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {2
        val binding = ItemVideoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is VideoViewHolder) {
            holder.bind(videos[position])
        }
    }

    override fun getItemCount(): Int = videos.size

    fun setVideos(list: List<TrailerResult>) {
        videos.clear()
        videos.addAll(list)
        notifyDataSetChanged()
    }
}