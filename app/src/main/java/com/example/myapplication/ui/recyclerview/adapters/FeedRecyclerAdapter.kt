package com.example.myapplication.ui.recyclerview.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BuildConfig
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemBackdropTitleBinding
import com.example.myapplication.databinding.ItemPersonBinding
import com.example.myapplication.databinding.ItemPosterBinding
import com.example.myapplication.databinding.ItemViewMoreBinding
import com.example.myapplication.models.pojo.*
import com.example.myapplication.ui.recyclerview.listeners.MoviePersonAndViewMoreAndTvClickListener
import com.example.myapplication.utils.setImage
import com.example.myapplication.viewmodel.MainScreenRequest

class FeedRecyclerAdapter(
    private val listener: MoviePersonAndViewMoreAndTvClickListener,
    private val request: MainScreenRequest,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {

    var mMoviesList: MutableList<BaseItem> = arrayListOf()

    class PosterViewHolder(val binding: ItemPosterBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Film) {
            binding.posterImage.setImage(buildImageUrl(movie))
        }

        private fun buildImageUrl(movie: Film): String {
            val imageUrl = BuildConfig.BASE_POSTER_URL + movie.posterPath
            Log.d("initImage", imageUrl)
            return imageUrl
        }
    }

    class BackdropAndTitleViewHolder(val binding: ItemBackdropTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tv: TV) {
            binding.backdropImage.setImage(buildImageUrl(tv))
            binding.title.text = tv.name
        }

        private fun buildImageUrl(tv: TV): String {
            val imageUrl = BuildConfig.BASE_BACKDROP_URL + tv.backdropPath
            Log.d("initImage", imageUrl)
            return imageUrl
        }
    }

    class PersonViewHolder(val binding: ItemPersonBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(person: Person) {
            binding.profileImage.setImage(buildImageUrl(person.profilePath))
            binding.name.text = person.name
        }

        fun buildImageUrl(profilePath: String?): String {
            val imageUrl = BuildConfig.BASE_PROFILE_URL + (profilePath ?: "")
            Log.d("initImage", imageUrl)
            return imageUrl
        }
    }

    class ViewMoreViewHolder(val binding: ItemViewMoreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(title: String) {
            binding.title.text = title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_poster -> {
                val binding =
                    ItemPosterBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false)
                binding.root.setOnClickListener(this)
                PosterViewHolder(binding)
            }
            R.layout.item_backdrop_title -> {
                val binding = ItemBackdropTitleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                binding.root.setOnClickListener(this)
                BackdropAndTitleViewHolder(binding)
            }
            R.layout.item_person -> {
                val binding =
                    ItemPersonBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false)
                binding.root.setOnClickListener(this)
                PersonViewHolder(binding)
            }
            else -> {
                val binding =
                    ItemViewMoreBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false)
                binding.root.setOnClickListener(this)
                ViewMoreViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tag = mMoviesList[position]
        when (holder) {
            is PosterViewHolder -> holder.bind(mMoviesList[position] as Film)
            is BackdropAndTitleViewHolder -> holder.bind(mMoviesList[position] as TV)
            is PersonViewHolder -> holder.bind(mMoviesList[position] as Person)
            is ViewMoreViewHolder -> holder.bind((mMoviesList[position] as HeaderItem).text)
        }
    }

    override fun getItemCount() = mMoviesList.size

    override fun getItemViewType(position: Int): Int {
        return when (mMoviesList[position]) {
            is Film -> R.layout.item_poster
            is TV -> R.layout.item_backdrop_title
            is Person -> R.layout.item_person
            else -> R.layout.item_view_more
        }
    }

    fun appendMovies(baseItems: List<BaseItem>) {
        mMoviesList.clear()
        mMoviesList.addAll(baseItems)
        mMoviesList.add(HeaderItem("Смотреть ещё"))
        notifyDataSetChanged()
    }

    override fun onClick(v: View?) {
        val movie = v?.tag as BaseItem
        when (movie) {
            is Film -> listener.onOpenMovie(movie.id)
            is TV -> listener.onOpenTV(movie.id)
            is Person -> listener.onOpenPerson(movie.id)
            is HeaderItem -> listener.onOpenMore(request)
        }
    }
}