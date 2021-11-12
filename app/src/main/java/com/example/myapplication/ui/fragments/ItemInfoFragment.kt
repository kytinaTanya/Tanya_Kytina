package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.BuildConfig
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentItemInfoBinding
import com.example.myapplication.firebase.AUTH
import com.example.myapplication.firebase.USER
import com.example.myapplication.models.movies.*
import com.example.myapplication.ui.activities.MainActivity.Companion.COLLECTION_TYPE
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
import com.example.myapplication.ui.recyclerview.listeners.*
import com.example.myapplication.utils.setConfigHorizontalLinearWithDiv
import com.example.myapplication.utils.setImage
import com.example.myapplication.viewmodel.ItemInfoViewModel
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

@AndroidEntryPoint
class ItemInfoFragment : Fragment(), AllSpecificListener, PhotoClickListener {

    private var _binding: FragmentItemInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ItemInfoViewModel by viewModels()
    private var film: FilmDetails? = null
    private var tv: TvDetails? = null
    private var currentUser: FirebaseUser? = null
    private var isFavorite: Boolean = false
    private var isInWatchlist: Boolean = false
    private var rating: Float = 0.0F
    private var currentRating = rating
    private val format = DecimalFormat("#,###.##")
    private var id: Long = 0L
    private var type: Int = 0
    private var season: Int? = null
    private var episode: Int? = null

    //Адаптеры для всех recyclerView
    private lateinit var genresAdapter: GenresRecyclerAdapter
    private lateinit var posterAdapter: ImagesRecyclerAdapter
    private lateinit var backdropAdapter: ImagesRecyclerAdapter
    private lateinit var videoAdapter: VideoRecyclerAdapter
    private lateinit var recommendationAdapter: RecommendationRecyclerAdapter
    private lateinit var similarAdapter: RecommendationRecyclerAdapter
    private lateinit var companiesAdapter: MovieRecyclerAdapter
    private lateinit var seasonAdapter: MovieRecyclerAdapter
    private lateinit var episodeAdapter: MovieRecyclerAdapter
    private lateinit var castAdapter: MovieRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentUser = AUTH.currentUser
        arguments?.let {
            id = it.getLong(MEDIA_ID)
            type = it.getInt(ITEM_TYPE)
            when (type) {
                MOVIE_TYPE -> {
                    viewModel.loadFilmDetails(id)
                    viewModel.loadMovieImages(id)
                    viewModel.loadMovieCast(id)
                    viewModel.loadMovieVideos(id)
                    viewModel.loadMovieRecommendations(id)
                    viewModel.loadMovieSimilar(id)
                    viewModel.loadMovieStates(id, USER.sessionKey)
                }
                TV_TYPE -> {
                    viewModel.loadTVDetails(id)
                    viewModel.loadTvImages(id)
                    viewModel.loadTvCast(id)
                    viewModel.loadTvVideos(id)
                    viewModel.loadTvRecommendations(id)
                    viewModel.loadTvSimilar(id)
                    viewModel.loadTvStates(id, USER.sessionKey)
                }
                PERSON_TYPE -> {
                    viewModel.loadPersonDetails(id)
                    viewModel.loadPersonImages(id)
                }
                SEASON_TYPE -> {
                    season = it.getInt(SEASON)
                    viewModel.loadSeasonDetails(id, season!!)
                }
                EPISODE_TYPE -> {
                    season = it.getInt(SEASON)
                    episode = it.getInt(EPISODE)
                    viewModel.loadEpisodeDetails(id, season!!, episode!!)
                }
                COLLECTION_TYPE -> {
                    viewModel.loadCollectionDetails(id.toInt())
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initComponents()
        setObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setObservers() {
        viewModel.baseItemDetails.observe(viewLifecycleOwner) { item ->
            when (item) {
                is FilmDetails -> {
                    film = item
                    Log.d("InitImage", "${BuildConfig.BASE_POSTER_URL}${item.posterPath}")
                    initUiData(
                        item.posterPath,
                        item.title,
                        item.releaseDate,
                        item.rating,
                        item.overview,
                        item.tagline,
                        item.genres,
                        formatBudget(item.budget, "Бюджет: "),
                        formatBudget(item.revenue, "Сборы: "),
                        item.collection,
                        item.companies,
                        item.countries,
                        "${item.originalTitle} (${item.runtime} m)",
                        item.homepage,
                        emptyList(),
                        emptyList(),
                        false
                    )
                    binding.createdBy.visibility = View.GONE
                }
                is TvDetails -> {
                    tv = item
                    initUiData(
                        item.posterPath,
                        item.name,
                        item.firstAirDate,
                        item.rating,
                        item.overview,
                        item.tagline,
                        item.genres,
                        formatSeasons(item.numOfSeasons, "Количество сезовонов: "),
                        formatSeasons(item.episodes, "Количество серий: "),
                        item.lastEpisode,
                        item.companies,
                        item.countries,
                        "${item.originalName} (${item.runtime} m)",
                        item.homepage,
                        item.createdBy,
                        item.seasons,
                        false
                    )
                }
                is PersonDetails -> {
                    initUiData(
                        item.profilePath,
                        item.name,
                        "",
                        item.popularity,
                        item.biography,
                        item.birthday,
                        emptyList(),
                        formatGender(item.gender),
                        item.placeOfBirth,
                        null,
                        emptyList(),
                        emptyList(),
                        "",
                        item.homepage,
                        emptyList(),
                        emptyList(),
                        false
                    )
                }
                is SeasonDetails -> {
                    initUiData(
                        item.posterPath,
                        item.name,
                        item.airDate,
                        0.0,
                        item.overview,
                        "",
                        emptyList(),
                        "",
                        "",
                        null,
                        emptyList(),
                        emptyList(),
                        "Номер сезона: " + item.number,
                        "",
                        emptyList(),
                        emptyList(),
                        false
                    )
                    initEpisodeRecyclerView()
                    binding.posterText.visibility = View.VISIBLE
                    binding.posterRecyclerview.visibility = View.VISIBLE
                    episodeAdapter.appendMovies(item.episodes)
                    binding.posterText.text = "Серии"
                }
                is EpisodeDetails -> {
                    initUiData(
                        item.stillPath,
                        item.name,
                        item.airDate,
                        0.0,
                        item.overview,
                        "",
                        emptyList(),
                        "",
                        "",
                        null,
                        emptyList(),
                        emptyList(),
                        "",
                        "",
                        emptyList(),
                        emptyList(),
                        true
                    )
                }
                is MovieCollection -> {
                    initUiData(
                        item.backdrop,
                        item.name,
                        "",
                        0.0,
                        item.overview,
                        "",
                        emptyList(),
                        "",
                        "",
                        null,
                        emptyList(),
                        emptyList(),
                        "",
                        "",
                        emptyList(),
                        emptyList(),
                        true
                    )
                    initEpisodeRecyclerView()
                    binding.posterText.visibility = View.VISIBLE
                    binding.posterRecyclerview.visibility = View.VISIBLE
                    episodeAdapter.appendMovies(item.parts)
                    binding.posterText.text = "Состав коллекции"
                }
            }
        }

        viewModel.movieStates.observe(viewLifecycleOwner) { movie ->
            isFavorite = movie.favorite
            isInWatchlist = movie.watchlist
            rating = movie.rating.rating
            binding.starBtn.visibility = View.VISIBLE
            binding.watchlistBtn.visibility = View.VISIBLE
            binding.loveBtn.visibility = View.VISIBLE
            if (isInWatchlist) {
                binding.watchlistBtn.setBackgroundResource(R.drawable.ic_bookmark_marked)
            } else {
                binding.watchlistBtn.setBackgroundResource(R.drawable.ic_turned_in)
            }
            if (isFavorite) {
                binding.loveBtn.setBackgroundResource(R.drawable.ic_favorite_marked)
            } else {
                binding.loveBtn.setBackgroundResource(R.drawable.ic_favorite)
            }
            if (rating > 0.0) {
                binding.starBtn.setBackgroundResource(R.drawable.ic_baseline_star_marked)
            } else {
                binding.starBtn.setBackgroundResource(R.drawable.ic_star)
            }
        }

        viewModel.addToWatchlistState.observe(viewLifecycleOwner) { status ->
            if (status.status == 1 || status.status == 12 || status.status == 13) {
                isInWatchlist = !isInWatchlist
            } else {
                Toast.makeText(requireContext(), "Попробуйсте еще раз", Toast.LENGTH_SHORT).show()
            }
            if (isInWatchlist) {
                binding.watchlistBtn.setBackgroundResource(R.drawable.ic_bookmark_marked)
            } else {
                binding.watchlistBtn.setBackgroundResource(R.drawable.ic_turned_in)
            }
        }

        viewModel.markAsFavState.observe(viewLifecycleOwner) { status ->
            if (status.status == 1 || status.status == 12 || status.status == 13) {
                isFavorite = !isFavorite
            } else {
                Toast.makeText(requireContext(), "Попробуйсте еще раз", Toast.LENGTH_SHORT).show()
            }
            if (isFavorite) {
                binding.loveBtn.setBackgroundResource(R.drawable.ic_favorite_marked)
            } else {
                binding.loveBtn.setBackgroundResource(R.drawable.ic_favorite)
            }
        }

        viewModel.ratedState.observe(viewLifecycleOwner) {
            if (it.status == 1 || it.status == 12 || it.status == 13) {
                rating = currentRating
            } else {
                currentRating = rating
                Toast.makeText(requireContext(), "Попробуйсте еще раз", Toast.LENGTH_SHORT).show()
            }
            binding.starBtn.setBackgroundResource(R.drawable.ic_baseline_star_marked)
            binding.ratingBar.rating = rating / 2
        }

        viewModel.posterPaths.observe(viewLifecycleOwner) { posters ->
            if(posters.isNotEmpty()) {
                binding.posterText.visibility = View.VISIBLE
                binding.posterRecyclerview.visibility = View.VISIBLE
                posterAdapter.setImages(posters)
            }
        }

        viewModel.backdropPaths.observe(viewLifecycleOwner) { backdrops ->
            if(backdrops.isNotEmpty()) {
                binding.backdropText.visibility = View.VISIBLE
                binding.backdropRecyclerview.visibility = View.VISIBLE
                backdropAdapter.setImages(backdrops)
            }
        }

        viewModel.profilePaths.observe(viewLifecycleOwner) { profiles ->
            if(profiles.isNotEmpty()) {
                binding.posterText.visibility = View.VISIBLE
                binding.posterRecyclerview.visibility = View.VISIBLE
                binding.posterText.text = "Фото"
                posterAdapter.setImages(profiles)
            }
        }

        viewModel.movieVideos.observe(viewLifecycleOwner) { videos ->
            if(videos.isNotEmpty()) {
                binding.videoText.visibility = View.VISIBLE
                binding.videoRecyclerview.visibility = View.VISIBLE
                videoAdapter.setVideos(videos)
            }
        }

        viewModel.recommendations.observe(viewLifecycleOwner) { recs ->
            if(recs.isNotEmpty()) {
                binding.recommendationText.visibility = View.VISIBLE
                binding.recommendationRecyclerview.visibility = View.VISIBLE
                recommendationAdapter.setItems(recs)
            }
        }

        viewModel.similar.observe(viewLifecycleOwner) { similars ->
            if(similars.isNotEmpty()) {
                binding.similarText.visibility = View.VISIBLE
                binding.similarRecyclerview.visibility = View.VISIBLE
                similarAdapter.setItems(similars)
            }
        }

        viewModel.cast.observe(viewLifecycleOwner) { cast ->
            if(cast.isNotEmpty()) {
                binding.mainRolesTitle.visibility = View.VISIBLE
                binding.mainRoles.visibility = View.VISIBLE
                castAdapter.appendMovies(cast)
            }
        }
    }

    private fun initComponents() {
        initRecyclerViews()

        binding.loveBtn.setOnClickListener {
            if (film != null) {
                viewModel.markAsFavorite(film!!.id, "movie", !isFavorite, USER.sessionKey)
            } else if (tv != null) {
                viewModel.markAsFavorite(tv!!.id, "tv", !isFavorite, USER.sessionKey)
            }
        }

        binding.watchlistBtn.setOnClickListener {
            if (film != null) {
                viewModel.addToWatchlist(film!!.id, "movie", !isInWatchlist, USER.sessionKey)
            } else if (tv != null) {
                viewModel.addToWatchlist(tv!!.id, "tv", !isInWatchlist, USER.sessionKey)
            }
        }

        binding.starBtn.setOnClickListener {
            if (binding.ratingBar.visibility == View.VISIBLE) {
                binding.ratingBar.visibility = View.GONE
            } else {
                binding.ratingBar.visibility = View.VISIBLE
                binding.ratingBar.rating = rating / 2
            }
        }

        binding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            if (fromUser) {
                if (film != null) {
                    viewModel.rateMovie(film!!.id, USER.sessionKey, rating * 2)
                }

                if (tv != null) {
                    viewModel.rateTv(tv!!.id, USER.sessionKey, rating * 2)
                }
                currentRating = rating * 2
            }
        }
    }

    private fun initRecyclerViews() {
        genresAdapter = GenresRecyclerAdapter()
        val genresLayoutManager = FlexboxLayoutManager(requireContext())
        genresLayoutManager.justifyContent = JustifyContent.FLEX_START
        binding.genres.apply {
            layoutManager = genresLayoutManager
            adapter = genresAdapter
            addItemDecoration(DividerItemDecoration(8))
        }

        companiesAdapter = MovieRecyclerAdapter(this)
        val companiesLayoutManager = FlexboxLayoutManager(requireContext())
        companiesLayoutManager.justifyContent = JustifyContent.FLEX_START
        binding.companies.apply {
            layoutManager = companiesLayoutManager
            adapter = companiesAdapter
            addItemDecoration(DividerItemDecoration(8))
        }

        posterAdapter = ImagesRecyclerAdapter(this)
        backdropAdapter = ImagesRecyclerAdapter(this)
        videoAdapter = VideoRecyclerAdapter()
        recommendationAdapter = RecommendationRecyclerAdapter(this)
        similarAdapter = RecommendationRecyclerAdapter(this)
        seasonAdapter = MovieRecyclerAdapter(this)
        castAdapter = MovieRecyclerAdapter(this)

        binding.apply {
            posterRecyclerview.setConfigHorizontalLinearWithDiv(posterAdapter, requireContext(), 16)
            backdropRecyclerview.setConfigHorizontalLinearWithDiv(backdropAdapter, requireContext(), 16)
            videoRecyclerview.setConfigHorizontalLinearWithDiv(videoAdapter, requireContext(), 16)
            recommendationRecyclerview.setConfigHorizontalLinearWithDiv(recommendationAdapter, requireContext(), 16)
            similarRecyclerview.setConfigHorizontalLinearWithDiv(similarAdapter, requireContext(), 16)
            seasons.setConfigHorizontalLinearWithDiv(seasonAdapter, requireContext(), 16)
            mainRoles.setConfigHorizontalLinearWithDiv(castAdapter, requireContext(), 16)
        }
    }

    private fun initEpisodeRecyclerView() {
        episodeAdapter = MovieRecyclerAdapter(this)
        binding.posterRecyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = episodeAdapter
            addItemDecoration(DividerItemDecoration(16))
        }
    }

    private fun initUiData(
        posterPath: String,
        title: String,
        releaseDate: String,
        rating: Number,
        overview: String,
        tagline: String,
        genres: List<MovieGenres>,
        budget: String,
        revenue: String,
        collection: BaseItem?,
        companies: List<ProductionCompanies>,
        countries: List<ProductionCounties>,
        subtitle: String,
        homepage: String,
        createdBy: List<TvProducer>,
        seasons: List<Season>,
        isAlbumPath: Boolean
    ) {

        if(isAlbumPath) {
            binding.movieImage.visibility = View.GONE
            binding.stillImage.visibility = View.VISIBLE
            binding.stillImage.setImage(BuildConfig.BASE_STILL_URL + posterPath)
            binding.stillImage.setOnClickListener {
                onOpenPicture(BuildConfig.BASE_STILL_URL + posterPath)
            }
        } else {
            binding.movieImage.setImage(BuildConfig.BASE_POSTER_URL + posterPath)
            binding.movieImage.setOnClickListener {
                onOpenPicture(BuildConfig.BASE_POSTER_URL + posterPath)
            }
        }
        setIfIsNotEmpty(title, binding.movieTitle)
        setIfIsNotEmpty(releaseDate, binding.yearOfMovie)
        setIfIsNotEmpty(overview, binding.movieAnnotation)
        setIfIsNotEmpty(tagline, binding.tagline)
        setIfIsNotEmpty(rating.toString(), binding.movieRating)
        setIfIsNotEmpty(budget, binding.budget)
        setIfIsNotEmpty(revenue, binding.revenue)
        if(genres.isEmpty()) {
            binding.genres.visibility = View.GONE
        } else {
            genresAdapter.setGenres(genres)
        }

        if(collection == null) {
            binding.collection.visibility = View.GONE
        } else {
            when(collection) {
                is MovieCollection -> {
                    initCollectionWidget(
                        "Коллекция",
                        "${BuildConfig.BASE_BACKDROP_URL}${collection?.backdrop}",
                        collection.name)
                    binding.collection.setOnClickListener {
                        onOpenCollection(collection.id)
                    }
                }
                is Episode -> {
                    if(collection.stillPath == "null" || collection.name == "") {
                        binding.collection.visibility = View.GONE
                    } else {
                        initCollectionWidget(
                            "Последний эпизод",
                            "${BuildConfig.BASE_STILL_URL}${collection.stillPath}",
                            collection.name)
                        binding.collection.setOnClickListener {
                            Log.d("LAST EPISODE", "${collection.showId} ${collection.seasonNum} ${collection.episodeNum}")
                            onOpenEpisode(collection.showId, collection.seasonNum, collection.episodeNum)

                        }
                    }
                }
            }
        }

        if(companies.isEmpty()) {
            binding.companies.visibility = View.GONE
        } else {
            companiesAdapter.appendMovies(companies)
        }
        setIfIsNotEmpty(createCountriesList(countries), binding.countries)
        setIfIsNotEmpty(subtitle, binding.originalTitle)
        if(homepage == null || homepage == "") {
            binding.homepage.visibility = View.GONE
        } else {
            binding.homepage.text = "Домашняя страница: $homepage"
        }

        setIfIsNotEmpty(createCreatedByList(createdBy), binding.createdBy)
        if(seasons.isEmpty()) {
            binding.seasons.visibility = View.GONE
        } else {
            seasonAdapter.appendMovies(seasons)
        }
        if(binding.createdBy.visibility == View.GONE &&
            binding.countries.visibility == View.GONE &&
            binding.companies.visibility == View.GONE) {
            binding.companyTitle.visibility = View.GONE
        }
    }

    private fun setIfIsNotEmpty(text: String, view: TextView) {
        if(text == "") {
            view.visibility = View.GONE
        } else {
            view.text = text
        }
    }

    private fun initCollectionWidget(type: String, image: String, name: String) {
        binding.isCollection.text = type
        binding.collectionImage.setImage(image)
        binding.collectionTitle.text = name
    }

    private fun createCreatedByList(createdBy: List<TvProducer>): String {
        var str = ""
        createdBy.forEach {
            str += it.name + ", "
        }
        str = str.dropLast(2)
        if(str == "") {
            return str
        } else {
            return "Создано: $str"
        }
    }

    private fun createCountriesList(countries: List<ProductionCounties>): String {
        var str = ""
        countries.forEach {
            str += it.name + ", "
        }
        str = str.dropLast(2)
        return if(str == "") {
            str
        } else {
            "Страны производства: $str"
        }
    }

    private fun formatGender(gender: Int): String {
        return if(gender == null) {
            ""
        } else {
            "Гендер: " +
                    when(gender) {
                        1 -> "Женщина"
                        2 -> "Мужчина"
                        else -> "Другой"
                    }
        }
    }

    private fun formatSeasons(num: Int, prefix: String): String {
        return if(num == 0) {
            ""
        } else {
            "$prefix: $num"
        }
    }

    private fun formatBudget(budget: Int, prefix: String): String {
        return if(budget == 0) {
            ""
        } else {
            "$prefix: \$ ${formatCost(budget)}"
        }
    }

    private fun formatCost(budget: Int): String {
        return format.format(budget).toString()
    }

    override fun onOpenMovie(id: Long) {
        val action = ItemInfoFragmentDirections.actionItemInfoFragmentSelf(id,
            MOVIE_TYPE, 0, 0)
        view?.findNavController()?.navigate(action)
    }

    override fun onOpenTV(id: Long) {
        val action = ItemInfoFragmentDirections.actionItemInfoFragmentSelf(id,
            TV_TYPE, 0, 0)
        view?.findNavController()?.navigate(action)
    }

    override fun onOpenPerson(id: Long) {
        val action = ItemInfoFragmentDirections.actionItemInfoFragmentSelf(id,
            PERSON_TYPE, 0, 0)
        view?.findNavController()?.navigate(action)
    }

    override fun onOpenSeason(id: Long, season: Int) {
        val action = ItemInfoFragmentDirections.actionItemInfoFragmentSelf(id,
            SEASON_TYPE, season, 0)
        view?.findNavController()?.navigate(action)
    }

    override fun onOpenEpisode(tvId: Long, seasonNum: Int, episode: Int) {
        val action = ItemInfoFragmentDirections.actionItemInfoFragmentSelf(id,
            EPISODE_TYPE, seasonNum, episode)
        view?.findNavController()?.navigate(action)
    }

    override fun onOpenCollection(id: Int) {
        val action = ItemInfoFragmentDirections.actionItemInfoFragmentSelf(id.toLong(),
            COLLECTION_TYPE, 0, 0)
        view?.findNavController()?.navigate(action)
    }

    override fun onOpenPicture(url: String) {
        val action = ItemInfoFragmentDirections.actionItemInfoFragmentToPhotoFragment(url)
        view?.findNavController()?.navigate(action)
    }
}