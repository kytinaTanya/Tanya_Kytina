package com.example.myapplication.ui.recyclerview.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ItemVideoBinding
import com.example.myapplication.models.movies.VideoResult
import com.example.myapplication.utils.setImage

class VideoRecyclerAdapter() : RecyclerView.Adapter<VideoRecyclerAdapter.VideoViewHolder>() {

    private val videos: MutableList<VideoResult> = arrayListOf()

    class VideoViewHolder(private val binding: ItemVideoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(video: VideoResult) {
//            Glide.with(context)
//                .asBitmap()
//                .load("https://youtu.be/${video.key}.mp4")
//                .into(binding.videoPlayer)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
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