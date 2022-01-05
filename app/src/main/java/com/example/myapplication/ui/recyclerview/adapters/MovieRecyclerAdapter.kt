package com.example.myapplication.ui.recyclerview.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BuildConfig
import com.example.myapplication.R
import com.example.myapplication.databinding.*
import com.example.myapplication.models.pojo.*
import com.example.myapplication.utils.setImage

class MovieRecyclerAdapter(private val listener: MovieClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {

    var mMoviesList: MutableList<BaseItem> = arrayListOf()

    class PosterViewHolder(val binding: ItemPosterBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Film){
            binding.posterImage.setImage(buildImageUrl(movie))
        }

        private fun buildImageUrl(movie: Film): String {
            val imageUrl = BuildConfig.BASE_POSTER_URL + movie.posterPath
            Log.d("initImage", imageUrl)
            return imageUrl
        }
    }

    class BackdropAndTitleViewHolder(val binding: ItemBackdropTitleBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(tv: TV){
            binding.backdropImage.setImage(buildImageUrl(tv))
            binding.title.text = tv.name
        }

        fun buildImageUrl(tv: TV): String {
            val imageUrl = BuildConfig.BASE_BACKDROP_URL + tv.backdropPath
            Log.d("initImage", imageUrl)
            return imageUrl
        }
    }

    class PersonViewHolder(val binding: ItemPersonBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(person: Person) {
            binding.profileImage.setImage(buildImageUrl(person.profilePath))
            binding.name.text = person.name
        }

        fun bind(crew: Crew) {
            binding.profileImage.setImage(buildImageUrl(crew.profile))
            binding.name.text = crew.name
        }

        fun bind(cast: Cast) {
            if(cast.profile == null) {
                binding.profileImage.setImage(buildImageUrl(""))
            } else {
                binding.profileImage.setImage(buildImageUrl(cast.profile))
            }
            binding.name.text = cast.name
        }

        fun buildImageUrl(profilePath: String): String {
            val imageUrl = BuildConfig.BASE_PROFILE_URL + profilePath
            Log.d("initImage", imageUrl)
            return imageUrl
        }
    }

    class CompanyViewHolder(val binding: ItemLogoBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(company: ProductionCompanies) {
            binding.logo.text = company.name
        }
    }

    class SeasonViewHolder(val binding: ItemSeasonBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(season: Season) {
            if(season.posterPath == null) {
                binding.poster.visibility = View.GONE
            } else {
                binding.poster.setImage(BuildConfig.BASE_POSTER_URL + season.posterPath)
            }
            binding.seasonName.text = season.name
            if(season.number == 0) {
                binding.numEpisodes.visibility = View.GONE
            } else {
                binding.numEpisodes.text = "Сезон ${season.number} \nКоличество эпизодов ${season.episodes}\nДата выхода " + season?.airDate ?: "неизвестна"
            }
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
        return when(viewType) {
            R.layout.item_poster -> {
                val binding =
                    ItemPosterBinding.inflate(LayoutInflater.from(parent.context),
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
                    ItemPersonBinding.inflate(LayoutInflater.from(parent.context),
                        parent,
                        false)
                binding.root.setOnClickListener(this)
                PersonViewHolder(binding)
            }
            R.layout.item_season -> {
                val binding =
                    ItemSeasonBinding.inflate(LayoutInflater.from(parent.context),
                        parent,
                        false)
                binding.root.setOnClickListener(this)
                SeasonViewHolder(binding)
            }
            R.layout.item_episodes -> {
                val binding =
                    ItemEpisodesBinding.inflate(LayoutInflater.from(parent.context),
                        parent,
                        false)
                binding.root.setOnClickListener(this)
                EpisodeViewHolder(binding)
            }
            else -> {
                val binding =
                    ItemLogoBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                binding.root.setOnClickListener(this)
                CompanyViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tag = mMoviesList[position]
        when(holder) {
            is PosterViewHolder -> holder.bind(mMoviesList[position] as Film)
            is BackdropAndTitleViewHolder -> holder.bind(mMoviesList[position] as TV)
            is PersonViewHolder -> {
                when(mMoviesList[position]) {
                    is Person -> holder.bind(mMoviesList[position] as Person)
                    is Crew -> holder.bind(mMoviesList[position] as Crew)
                    is Cast -> holder.bind(mMoviesList[position] as Cast)
                }
            }
            is SeasonViewHolder -> holder.bind(mMoviesList[position] as Season)
            is EpisodeViewHolder -> holder.bind(mMoviesList[position] as Episode)
            is CompanyViewHolder -> holder.bind(mMoviesList[position] as ProductionCompanies)
        }
    }

    override fun getItemCount() = mMoviesList.size

    override fun getItemViewType(position: Int): Int {
        return when(mMoviesList[position]) {
            is Film -> R.layout.item_poster
            is TV -> R.layout.item_backdrop_title
            is Person -> R.layout.item_person
            is Crew -> R.layout.item_person
            is Cast -> R.layout.item_person
            is Season -> R.layout.item_season
            is Episode -> R.layout.item_episodes
            else  -> R.layout.item_logo
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
            is TV -> listener.onOpenTV(movie.id)
            is Person -> listener.onOpenPerson(movie.id)
            is Crew -> listener.onOpenPerson(movie.id)
            is Cast -> listener.onOpenPerson(movie.id)
            is Season -> listener.onOpenSeason(movie.showId, movie.number)
            is Episode -> listener.onOpenEpisode(movie.showId, movie.seasonNum, movie.episodeNum)
        }
    }
}