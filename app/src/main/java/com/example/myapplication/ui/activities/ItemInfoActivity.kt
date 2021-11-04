package com.example.myapplication.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.BuildConfig
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityItemInfoBinding
import com.example.myapplication.firebase.AUTH
import com.example.myapplication.firebase.USER
import com.example.myapplication.models.movies.*
import com.example.myapplication.ui.activities.MainActivity.Companion.EPISODE
import com.example.myapplication.ui.activities.MainActivity.Companion.EPISODE_TYPE
import com.example.myapplication.ui.activities.MainActivity.Companion.ITEM_TYPE
import com.example.myapplication.ui.activities.MainActivity.Companion.MEDIA_ID
import com.example.myapplication.ui.activities.MainActivity.Companion.MOVIE_TYPE
import com.example.myapplication.ui.activities.MainActivity.Companion.PERSON_TYPE
import com.example.myapplication.ui.activities.MainActivity.Companion.SEASON
import com.example.myapplication.ui.activities.MainActivity.Companion.SEASON_TYPE
import com.example.myapplication.ui.activities.MainActivity.Companion.TV_TYPE
import com.example.myapplication.ui.recyclerview.DividerItemDecoration
import com.example.myapplication.ui.recyclerview.adapters.*
import com.example.myapplication.utils.setImage
import com.example.myapplication.viewmodel.ItemInfoViewModel
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

@AndroidEntryPoint
class ItemInfoActivity : AppCompatActivity(), MovieClickListener, PhotoClickListener {

//    private lateinit var binding: ActivityItemInfoBinding
//    private val viewModel: ItemInfoViewModel by viewModels()
//    private var film: FilmDetails? = null
//    private var tv: TvDetails? = null
//    private var listId = USER.historyListID
//    private var currentUser: FirebaseUser? = null
//    private var isFavorite: Boolean = false
//    private var isInWatchlist: Boolean = false
//    private var rating: Float = 0.0F
//    private var currentRating = rating
//    private val format = DecimalFormat("#,###.##")
//    //Дополнительная переменная currentRating нужна, чтобы при неудачной попытке оченить фильм/сериал, можно было откатить назад
//
//    //Адаптеры для всех recyclerView
//    private lateinit var genresAdapter: GenresRecyclerAdapter
//    private lateinit var posterAdapter: ImagesRecyclerAdapter
//    private lateinit var backdropAdapter: ImagesRecyclerAdapter
//    private lateinit var videoAdapter: VideoRecyclerAdapter
//    private lateinit var recommendationAdapter: RecommendationRecyclerAdapter
//    private lateinit var similarAdapter: RecommendationRecyclerAdapter
//    private lateinit var companiesAdapter: MovieRecyclerAdapter
//    private lateinit var seasonAdapter: MovieRecyclerAdapter
//    private lateinit var episodeAdapter: MovieRecyclerAdapter
//    private lateinit var castAdapter: MovieRecyclerAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityItemInfoBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        initComponents()
//        currentUser = AUTH.currentUser
//
//        val id: Long = (intent.extras?.get(MEDIA_ID) ?: 1L) as Long
//        when (intent.extras?.get(ITEM_TYPE)) {
//            MOVIE_TYPE -> {
//                viewModel.loadFilmDetails(id)
//                viewModel.loadMovieImages(id)
//                viewModel.loadMovieCast(id)
//                viewModel.loadMovieVideos(id)
//                viewModel.loadMovieRecommendations(id)
//                viewModel.loadMovieSimilar(id)
//                viewModel.loadMovieStates(id, USER.sessionKey)
//                binding.createdBy.visibility = View.GONE
//            }
//            TV_TYPE -> {
//                viewModel.loadTVDetails(id)
//                viewModel.loadTvImages(id)
//                viewModel.loadTvCast(id)
//                viewModel.loadTvVideos(id)
//                viewModel.loadTvRecommendations(id)
//                viewModel.loadTvSimilar(id)
//                viewModel.loadTvStates(id, USER.sessionKey)
//            }
//            PERSON_TYPE -> {
//                viewModel.loadPersonDetails(id)
//                viewModel.loadPersonImages(id)
//            }
//            SEASON_TYPE -> {
//                val season: Int = (intent.extras?.get(SEASON) ?: 1) as Int
//                viewModel.loadSeasonDetails(id, season)
//                initEpisodeRecyclerView()
//            }
//            EPISODE_TYPE -> {
//                val season: Int = (intent.extras?.get(SEASON) ?: 1) as Int
//                val episode: Int = (intent.extras?.get(EPISODE) ?: 1) as Int
//                viewModel.loadEpisodeDetails(id, season, episode)
//            }
//        }
//    }
//
//    private fun initComponents() {
//        initRecyclerViews()
//
//        binding.loveBtn.setOnClickListener {
//            if (film != null) {
//                viewModel.markAsFavorite(film!!.id, "movie", !isFavorite, USER.sessionKey)
//            } else if (tv != null) {
//                viewModel.markAsFavorite(tv!!.id, "tv", !isFavorite, USER.sessionKey)
//            }
//        }
//
//        binding.watchlistBtn.setOnClickListener {
//            if (film != null) {
//                viewModel.addToWatchlist(film!!.id, "movie", !isInWatchlist, USER.sessionKey)
//            } else if (tv != null) {
//                viewModel.addToWatchlist(tv!!.id, "tv", !isInWatchlist, USER.sessionKey)
//            }
//        }
//
//        binding.starBtn.setOnClickListener {
//            if (binding.ratingBar.visibility == View.VISIBLE) {
//                binding.ratingBar.visibility = View.GONE
//            } else {
//                binding.ratingBar.visibility = View.VISIBLE
//                binding.ratingBar.rating = rating / 2
//            }
//        }
//
//        binding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
//            if (fromUser) {
//                if (film != null) {
//                    viewModel.rateMovie(film!!.id, USER.sessionKey, rating * 2)
//                }
//
//                if (tv != null) {
//                    viewModel.rateTv(tv!!.id, USER.sessionKey, rating * 2)
//                }
//                currentRating = rating * 2
//            }
//        }
//    }
//
//    private fun initRecyclerViews() {
//        genresAdapter = GenresRecyclerAdapter()
//        val genresLayoutManager = FlexboxLayoutManager(this)
//        genresLayoutManager.justifyContent = JustifyContent.FLEX_START
//        binding.genres.apply {
//            layoutManager = genresLayoutManager
//            adapter = genresAdapter
//            addItemDecoration(DividerItemDecoration(8))
//        }
//
//        posterAdapter = ImagesRecyclerAdapter(this)
//        binding.posterRecyclerview.apply {
//            layoutManager = LinearLayoutManager(
//                context,
//                LinearLayoutManager.HORIZONTAL,
//                false
//            )
//            adapter = posterAdapter
//            addItemDecoration(DividerItemDecoration(16))
//        }
//
//        backdropAdapter = ImagesRecyclerAdapter(this)
//        binding.backdropRecyclerview.apply {
//            layoutManager = LinearLayoutManager(
//                context,
//                LinearLayoutManager.HORIZONTAL,
//                false
//            )
//            adapter = backdropAdapter
//            addItemDecoration(DividerItemDecoration(16))
//        }
//
//        videoAdapter = VideoRecyclerAdapter()
//        binding.videoRecyclerview.apply {
//            layoutManager = LinearLayoutManager(
//                context,
//                LinearLayoutManager.HORIZONTAL,
//                false
//            )
//            adapter = videoAdapter
//            addItemDecoration(DividerItemDecoration(16))
//        }
//
//        recommendationAdapter = RecommendationRecyclerAdapter(this)
//        binding.recommendationRecyclerview.apply {
//            layoutManager = LinearLayoutManager(
//                context,
//                LinearLayoutManager.HORIZONTAL,
//                false
//            )
//            adapter = recommendationAdapter
//            addItemDecoration(DividerItemDecoration(16))
//        }
//
//        similarAdapter = RecommendationRecyclerAdapter(this)
//        binding.similarRecyclerview.apply {
//            layoutManager = LinearLayoutManager(
//                context,
//                LinearLayoutManager.HORIZONTAL,
//                false
//            )
//            adapter = similarAdapter
//            addItemDecoration(DividerItemDecoration(8))
//        }
//
//        companiesAdapter = MovieRecyclerAdapter(this)
//        val companiesLayoutManager = FlexboxLayoutManager(this)
//        companiesLayoutManager.justifyContent = JustifyContent.FLEX_START
//        binding.companies.apply {
//            layoutManager = companiesLayoutManager
//            adapter = companiesAdapter
//            addItemDecoration(DividerItemDecoration(8))
//        }
//
//        seasonAdapter = MovieRecyclerAdapter(this)
//        binding.seasons.apply {
//            layoutManager = LinearLayoutManager(
//                context,
//                LinearLayoutManager.HORIZONTAL,
//                false
//            )
//            adapter = seasonAdapter
//            addItemDecoration(DividerItemDecoration(16))
//        }
//
//        castAdapter = MovieRecyclerAdapter(this)
//        binding.mainRoles.apply {
//            layoutManager = LinearLayoutManager(
//                context,
//                LinearLayoutManager.HORIZONTAL,
//                false
//            )
//            adapter = castAdapter
//            addItemDecoration(DividerItemDecoration(16))
//        }
//    }
//
//    private fun initEpisodeRecyclerView() {
//        episodeAdapter = MovieRecyclerAdapter(this)
//        binding.posterRecyclerview.apply {
//            layoutManager = LinearLayoutManager(context)
//            adapter = episodeAdapter
//            addItemDecoration(DividerItemDecoration(16))
//        }
//    }
//
//    override fun onStart() {
//        super.onStart()
//        viewModel.baseItemDetails.observe(this) { item ->
//            when (item) {
//                is FilmDetails -> {
//                    film = item
//                    Log.d("InitImage", "${BuildConfig.BASE_POSTER_URL}${item.posterPath}")
//                    initUiData(
//                        item.posterPath,
//                        item.title,
//                        item.releaseDate,
//                        item.rating,
//                        item.overview,
//                        item.tagline,
//                        item.genres,
//                        formatBudget(item.budget, "Бюджет: "),
//                        formatBudget(item.revenue, "Сборы: "),
//                        item.collection,
//                        item.companies,
//                        item.countries,
//                        "${item.originalTitle} (${item.runtime} m)",
//                        item.homepage,
//                        emptyList(),
//                        emptyList(),
//                        false
//                    )
//                }
//                is TvDetails -> {
//                    tv = item
//                    initUiData(
//                        item.posterPath,
//                        item.name,
//                        item.firstAirDate,
//                        item.rating,
//                        item.overview,
//                        item.tagline,
//                        item.genres,
//                        formatSeasons(item.numOfSeasons, "Количество сезовонов: "),
//                        formatSeasons(item.episodes, "Количество серий: "),
//                        item.lastEpisode,
//                        item.companies,
//                        item.countries,
//                        "${item.originalName} (${item.runtime} m)",
//                        item.homepage,
//                        item.createdBy,
//                        item.seasons,
//                        false
//                    )
//                }
//                is PersonDetails -> {
//                    initUiData(
//                        item.profilePath,
//                        item.name,
//                        "",
//                        item.popularity,
//                        item.biography,
//                        item.birthday,
//                        emptyList(),
//                        formatGender(item.gender),
//                        item.placeOfBirth,
//                        null,
//                        emptyList(),
//                        emptyList(),
//                        "",
//                        item.homepage,
//                        emptyList(),
//                        emptyList(),
//                        false
//                    )
//                }
//                is SeasonDetails -> {
//                    initUiData(
//                        item.posterPath,
//                        item.name,
//                        item.airDate,
//                        0.0,
//                        item.overview,
//                        "",
//                        emptyList(),
//                        "",
//                        "",
//                        null,
//                        emptyList(),
//                        emptyList(),
//                        "Номер сезона: " + item.number,
//                        "",
//                        emptyList(),
//                        emptyList(),
//                        false
//                    )
//                    binding.posterText.visibility = View.VISIBLE
//                    binding.posterRecyclerview.visibility = View.VISIBLE
//                    episodeAdapter.appendMovies(item.episodes)
//                    binding.posterText.text = "Серии"
//                }
//                is EpisodeDetails -> {
//                    initUiData(
//                        item.stillPath,
//                        item.name,
//                        item.airDate,
//                        0.0,
//                        item.overview,
//                        "",
//                        emptyList(),
//                        "",
//                        "",
//                        null,
//                        emptyList(),
//                        emptyList(),
//                        "",
//                        "",
//                        emptyList(),
//                        emptyList(),
//                        true
//                    )
//                }
//            }
//        }
//
//        viewModel.movieStates.observe(this) { movie ->
//            isFavorite = movie.favorite
//            isInWatchlist = movie.watchlist
//            rating = movie.rating.rating
//            binding.starBtn.visibility = View.VISIBLE
//            binding.watchlistBtn.visibility = View.VISIBLE
//            binding.loveBtn.visibility = View.VISIBLE
//            if (isInWatchlist) {
//                binding.watchlistBtn.setBackgroundResource(R.drawable.ic_bookmark_marked)
//            } else {
//                binding.watchlistBtn.setBackgroundResource(R.drawable.ic_turned_in)
//            }
//            if (isFavorite) {
//                binding.loveBtn.setBackgroundResource(R.drawable.ic_favorite_marked)
//            } else {
//                binding.loveBtn.setBackgroundResource(R.drawable.ic_favorite)
//            }
//            if (rating > 0.0) {
//                binding.starBtn.setBackgroundResource(R.drawable.ic_baseline_star_marked)
//            } else {
//                binding.starBtn.setBackgroundResource(R.drawable.ic_star)
//            }
//        }
//
//        viewModel.addToWatchlistState.observe(this) { status ->
//            if (status.status == 1 || status.status == 12 || status.status == 13) {
//                isInWatchlist = !isInWatchlist
//            } else {
//                Toast.makeText(this, "Попробуйсте еще раз", Toast.LENGTH_SHORT).show()
//            }
//            if (isInWatchlist) {
//                binding.watchlistBtn.setBackgroundResource(R.drawable.ic_bookmark_marked)
//            } else {
//                binding.watchlistBtn.setBackgroundResource(R.drawable.ic_turned_in)
//            }
//        }
//
//        viewModel.markAsFavState.observe(this) { status ->
//            if (status.status == 1 || status.status == 12 || status.status == 13) {
//                isFavorite = !isFavorite
//            } else {
//                Toast.makeText(this, "Попробуйсте еще раз", Toast.LENGTH_SHORT).show()
//            }
//            if (isFavorite) {
//                binding.loveBtn.setBackgroundResource(R.drawable.ic_favorite_marked)
//            } else {
//                binding.loveBtn.setBackgroundResource(R.drawable.ic_favorite)
//            }
//        }
//
//        viewModel.ratedState.observe(this) {
//            if (it.status == 1 || it.status == 12 || it.status == 13) {
//                rating = currentRating
//            } else {
//                currentRating = rating
//                Toast.makeText(this, "Попробуйсте еще раз", Toast.LENGTH_SHORT).show()
//            }
//            binding.starBtn.setBackgroundResource(R.drawable.ic_baseline_star_marked)
//            binding.ratingBar.rating = rating / 2
//        }
//
//        viewModel.posterPaths.observe(this) { posters ->
//            if(posters.isNotEmpty()) {
//                binding.posterText.visibility = View.VISIBLE
//                binding.posterRecyclerview.visibility = View.VISIBLE
//                posterAdapter.setImages(posters)
//            }
//        }
//
//        viewModel.backdropPaths.observe(this) { backdrops ->
//            if(backdrops.isNotEmpty()) {
//                binding.backdropText.visibility = View.VISIBLE
//                binding.backdropRecyclerview.visibility = View.VISIBLE
//                backdropAdapter.setImages(backdrops)
//            }
//        }
//
//        viewModel.profilePaths.observe(this) { profiles ->
//            if(profiles.isNotEmpty()) {
//                binding.posterText.visibility = View.VISIBLE
//                binding.posterRecyclerview.visibility = View.VISIBLE
//                binding.posterText.text = "Фото"
//                posterAdapter.setImages(profiles)
//            }
//        }
//
//        viewModel.movieVideos.observe(this) { videos ->
//            if(videos.isNotEmpty()) {
//                binding.videoText.visibility = View.VISIBLE
//                binding.videoRecyclerview.visibility = View.VISIBLE
//                videoAdapter.setVideos(videos)
//            }
//        }
//
//        viewModel.recommendations.observe(this) { recs ->
//            if(recs.isNotEmpty()) {
//                binding.recommendationText.visibility = View.VISIBLE
//                binding.recommendationRecyclerview.visibility = View.VISIBLE
//                recommendationAdapter.setItems(recs)
//            }
//        }
//
//        viewModel.similar.observe(this) { similars ->
//            if(similars.isNotEmpty()) {
//                binding.similarText.visibility = View.VISIBLE
//                binding.similarRecyclerview.visibility = View.VISIBLE
//                similarAdapter.setItems(similars)
//            }
//        }
//
//        viewModel.cast.observe(this) { cast ->
//            if(cast.isNotEmpty()) {
//                binding.mainRolesTitle.visibility = View.VISIBLE
//                binding.mainRoles.visibility = View.VISIBLE
//                castAdapter.appendMovies(cast)
//            }
//        }
//    }
//
//    private fun formatGender(gender: Int): String {
//        return if(gender == null) {
//            ""
//        } else {
//            "Гендер: " +
//                    when(gender) {
//                        1 -> "Женщина"
//                        2 -> "Мужчина"
//                        else -> "Другой"
//                    }
//        }
//    }
//
//    private fun formatSeasons(num: Int, prefix: String): String {
//        return if(num == 0) {
//            ""
//        } else {
//            "$prefix: $num"
//        }
//    }
//
//    private fun formatBudget(budget: Int, prefix: String): String {
//        return if(budget == 0) {
//            ""
//        } else {
//            "$prefix: \$ ${formatCost(budget)}"
//        }
//    }
//
//    private fun initUiData(
//        posterPath: String,
//        title: String,
//        releaseDate: String,
//        rating: Number,
//        overview: String,
//        tagline: String,
//        genres: List<MovieGenres>,
//        budget: String,
//        revenue: String,
//        collection: BaseItem?,
//        companies: List<ProductionCompanies>,
//        countries: List<ProductionCounties>,
//        subtitle: String,
//        homepage: String,
//        createdBy: List<TvProducer>,
//        seasons: List<Season>,
//        isAlbumPath: Boolean
//    ) {
//
//        if(isAlbumPath) {
//            binding.movieImage.visibility = View.GONE
//            binding.stillImage.visibility = View.VISIBLE
//            binding.stillImage.setImage(BuildConfig.BASE_STILL_URL + posterPath)
//        } else {
//            binding.movieImage.setImage(BuildConfig.BASE_POSTER_URL + posterPath)
//        }
//        setIfIsNotEmpty(title, binding.movieTitle)
//        setIfIsNotEmpty(releaseDate, binding.yearOfMovie)
//        setIfIsNotEmpty(overview, binding.movieAnnotation)
//        setIfIsNotEmpty(tagline, binding.tagline)
//        setIfIsNotEmpty(rating.toString(), binding.movieRating)
//        setIfIsNotEmpty(budget, binding.budget)
//        setIfIsNotEmpty(revenue, binding.revenue)
//        if(genres.isEmpty()) {
//            binding.genres.visibility = View.GONE
//        } else {
//            genresAdapter.setGenres(genres)
//        }
//
//        if(collection == null) {
//            binding.collection.visibility = View.GONE
//        } else {
//            when(collection) {
//                is MovieCollection -> {
//                    initCollectionWidget(
//                        "Коллекция",
//                        "${BuildConfig.BASE_BACKDROP_URL}${collection?.backdrop}",
//                        collection.name)
//                }
//                is Episode -> {
//                    if(collection.stillPath == "null" || collection.name == "") {
//                        binding.collection.visibility = View.GONE
//                    } else {
//                        initCollectionWidget(
//                            "Последний эпизод",
//                            "${BuildConfig.BASE_STILL_URL}${collection.stillPath}",
//                            collection.name)
//                        binding.collection.setOnClickListener {
//                            Log.d("LAST EPISODE", "${collection.showId} ${collection.seasonNum} ${collection.episodeNum}")
//                            onOpenEpisode(collection.showId, collection.seasonNum, collection.episodeNum)
//
//                        }
//                    }
//                }
//            }
//        }
//
//        if(companies.isEmpty()) {
//            binding.companies.visibility = View.GONE
//        } else {
//            companiesAdapter.appendMovies(companies)
//        }
//        setIfIsNotEmpty(createCountriesList(countries), binding.countries)
//        setIfIsNotEmpty(subtitle, binding.originalTitle)
//        if(homepage == null || homepage == "") {
//            binding.homepage.visibility = View.GONE
//        } else {
//            binding.homepage.text = "Домашняя страница: $homepage"
//        }
//
//        setIfIsNotEmpty(createCreatedByList(createdBy), binding.createdBy)
//        if(seasons.isEmpty()) {
//            binding.seasons.visibility = View.GONE
//        } else {
//            seasonAdapter.appendMovies(seasons)
//        }
//        if(binding.createdBy.visibility == View.GONE &&
//            binding.countries.visibility == View.GONE &&
//            binding.companies.visibility == View.GONE) {
//            binding.companyTitle.visibility = View.GONE
//        }
//    }
//
//    private fun setIfIsNotEmpty(text: String, view: TextView) {
//        if(text == "") {
//            view.visibility = android.view.View.GONE
//        } else {
//            view.text = text
//        }
//    }
//
//    private fun initCollectionWidget(type: String, image: String, name: String) {
//        binding.isCollection.text = type
//        binding.collectionImage.setImage(image)
//        binding.collectionTitle.text = name
//    }
//
//    private fun createCreatedByList(createdBy: List<TvProducer>): String {
//        var str = ""
//        createdBy.forEach {
//            str += it.name + ", "
//        }
//        str = str.dropLast(2)
//        if(str == "") {
//            return str
//        } else {
//            return "Создано: $str"
//        }
//    }
//
//    private fun createCountriesList(countries: List<ProductionCounties>): String {
//        var str = ""
//        countries.forEach {
//            str += it.name + ", "
//        }
//        str = str.dropLast(2)
//        return if(str == "") {
//            str
//        } else {
//            "Страны производства: $str"
//        }
//    }
//
//    private fun formatCost(budget: Int): String {
//        return format.format(budget).toString()
//    }
//
    override fun onOpenMovie(id: Long) {
//        MainActivity.openMovie(id, MOVIE_TYPE, this)
    }

    override fun onOpenTV(id: Long) {
//        MainActivity.openMovie(id, TV_TYPE, this)
    }

    override fun onOpenPerson(id: Long) {
//        MainActivity.openMovie(id, PERSON_TYPE, this)
    }

    override fun onOpenSeason(id: Long, season: Int) {
//        MainActivity.openMovie(id, season, SEASON_TYPE, this)
    }

    override fun onOpenEpisode(tvId: Long, seasonNum: Int, episode: Int) {
//        MainActivity.openMovie(tvId, seasonNum, episode, EPISODE_TYPE, this)
    }

    override fun onOpenPicture(url: String) {
//        val intent = Intent(this, PictureActivity::class.java).apply {
//            putExtra(PICTURE_URL, url)
//        }
//        startActivity(intent)
    }
//
//    companion object {
//        const val PICTURE_URL = "picture"
//    }
}