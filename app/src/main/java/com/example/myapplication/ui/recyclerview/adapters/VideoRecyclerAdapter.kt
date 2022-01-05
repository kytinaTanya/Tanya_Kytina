package com.example.myapplication.ui.recyclerview.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemVideoBinding
import com.example.myapplication.models.pojo.VideoResult
import com.example.myapplication.utils.setImage

class VideoRecyclerAdapter() : RecyclerView.Adapter<VideoRecyclerAdapter.VideoViewHolder>() {

    private val videos: MutableList<VideoResult> = arrayListOf()

    class VideoViewHolder(private val binding: ItemVideoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(video: VideoResult) {
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

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(videos[position])
    }

    override fun getItemCount(): Int = videos.size

    fun setVideos(list: List<VideoResult>) {
        videos.clear()
        videos.addAll(list)
        notifyDataSetChanged()
    }
}