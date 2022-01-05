package com.example.myapplication.ui.recyclerview.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BuildConfig
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemBackdropBinding
import com.example.myapplication.databinding.ItemPosterBinding
import com.example.myapplication.models.pojo.ImageUrlPath
import com.example.myapplication.utils.setImage

class ImagesRecyclerAdapter(private val listener: PhotoClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {

    private val images: MutableList<ImageUrlPath> = arrayListOf()

    class PosterViewHolder(val binding: ItemPosterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(image: ImageUrlPath) {
            binding.posterImage.setImage("${BuildConfig.BASE_POSTER_URL}${image.path}")
        }
    }

    class BackdropViewHolder(val binding: ItemBackdropBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: ImageUrlPath) {
            binding.backdropImage.setImage("${BuildConfig.BASE_BACKDROP_URL}${image.path}")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_poster -> {
                val binding =
                    ItemPosterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                binding.root.setOnClickListener(this)
                PosterViewHolder(binding)
            }
            else -> {
                val binding =
                    ItemBackdropBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                binding.root.setOnClickListener(this)
                BackdropViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tag = images[position]
        when (holder) {
            is PosterViewHolder -> holder.bind(images[position])
            is BackdropViewHolder -> holder.bind(images[position])
        }
    }

    override fun getItemCount(): Int = images.size

    override fun getItemViewType(position: Int): Int {
        return if (images[position].height > images[position].width) {
            R.layout.item_poster
        } else {
            R.layout.item_backdrop
        }
    }

    fun setImages(list: List<ImageUrlPath>) {
        images.clear()
        images.addAll(list)
        notifyDataSetChanged()
    }

    override fun onClick(v: View?) {
        val image: ImageUrlPath = v?.tag as ImageUrlPath
        if(image.height > image.width) {
            listener.onOpenPicture(BuildConfig.BASE_POSTER_URL + image.path)
        } else {
            listener.onOpenPicture(BuildConfig.BASE_BACKDROP_URL + image.path)
        }

    }
}