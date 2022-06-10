package com.example.myapplication.ui.fragments.itemsinfos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.BuildConfig
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFilmInfoBinding
import com.example.myapplication.firebase.AUTH
import com.example.myapplication.firebase.USER
import com.example.myapplication.models.history.PostResponseStatus
import com.example.myapplication.models.pojo.ProductionCounties
import com.example.myapplication.states.FilmInfoStates
import com.example.myapplication.states.MarkedState
import com.example.myapplication.states.RatedState
import com.example.myapplication.ui.activities.MainActivity.Companion.MEDIA_ID
import com.example.myapplication.ui.recyclerview.RegularDividerItemDecoration
import com.example.myapplication.ui.recyclerview.adapters.*
import com.example.myapplication.ui.recyclerview.listeners.AllSpecificListenerAndTv
import com.example.myapplication.ui.recyclerview.listeners.PhotoClickListener
import com.example.myapplication.utils.*
import com.example.myapplication.utils.Utils.Companion.setIfIsNotEmpty
import com.example.myapplication.viewmodel.itemsinfos.MovieInfoViewModel
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

@AndroidEntryPoint
class FilmInfoFragment : Fragment(), AllSpecificListenerAndTv, PhotoClickListener {

    private var _binding: FragmentFilmInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieInfoViewModel by viewModels()
    private var filmId: Long? = null
    private var currentUser: FirebaseUser? = null
    private var isFavorite: Boolean = false
    private var isInWatchlist: Boolean = false
    private var rating: Float = 0.0F
    private val format = DecimalFormat("#,###.##")
    private var movieId: Long = 0L

    //Адаптеры для всех recyclerView
    private lateinit var genresAdapter: GenresRecyclerAdapter
    private lateinit var posterAdapter: ImagesRecyclerAdapter
    private lateinit var backdropAdapter: ImagesRecyclerAdapter
    private lateinit var videoAdapter: VideoRecyclerAdapter
    private lateinit var recommendationAdapter: RecommendationRecyclerAdapter
    private lateinit var similarAdapter: RecommendationRecyclerAdapter
    private lateinit var companiesAdapter: MovieRecyclerAdapter
    private lateinit var episodeAdapter: EpisodeRecyclerAdapter
    private lateinit var castAdapter: MovieRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentUser = AUTH.currentUser
        arguments?.let {
            movieId = it.getLong(MEDIA_ID)
        }
        viewModel.loadFilmDetails(movieId, USER.sessionKey)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilmInfoBinding.inflate(inflater, container, false)
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
        binding.errorButton.setOnClickListener { viewModel.loadFilmDetails(movieId, USER.sessionKey) }
        viewModel.movieDetails.observe(viewLifecycleOwner) { state ->
            if (handleLoadingAndError(state) && state is FilmInfoStates.Success) {
                val movie = state.data
                filmId = movie.id
                binding.mainImage.apply {
                    visibility = View.VISIBLE
                    setImage(BuildConfig.BASE_POSTER_URL + movie.posterPath)
                    setOnClickListener { onOpenPicture(BuildConfig.BASE_POSTER_URL + movie.posterPath) }
                }
                binding.toolbar.title = movie.title
                binding.toolbar.setNavigationOnClickListener {
                    view?.findNavController()?.popBackStack()
                }
                binding.loadingToolbar.setNavigationOnClickListener {
                    view?.findNavController()?.popBackStack()
                }
                binding.errorToolbar.setNavigationOnClickListener {
                    view?.findNavController()?.popBackStack()
                }
                setIfIsNotEmpty(movie.title, binding.movieTitle)
                setIfIsNotEmpty(movie.releaseDate, binding.yearOfMovie)
                setIfIsNotEmpty(movie.overview, binding.movieAnnotation)
                setIfIsNotEmpty(movie.tagline, binding.tagline)
                setIfIsNotEmpty("Общая оценка: ${movie.rating}", binding.movieRating)
                setIfIsNotEmpty(formatBudget(movie.budget, "Бюджет"), binding.budget)
                setIfIsNotEmpty(formatBudget(movie.revenue, "Сборы"), binding.revenue)
                if(movie.genres.isEmpty()) {
                    binding.genres.visibility = View.GONE
                } else {
                    genresAdapter.setGenres(movie.genres)
                }

                if(movie.collection == null) {
                    binding.collection.visibility = View.GONE
                } else {
                    binding.isCollection.text = "Коллекция"
                    binding.collectionImage.setImage(BuildConfig.BASE_BACKDROP_URL + movie.collection.backdrop)
                    binding.collectionTitle.text = movie.collection.name
                    binding.collection.setOnClickListener {
                        onOpenCollection(movie.collection.id)
                    }
                }

                if(movie.companies.isEmpty()) {
                    binding.companies.visibility = View.GONE
                } else {
                    companiesAdapter.appendMovies(movie.companies)
                }
                setIfIsNotEmpty(createCountriesList(movie.countries), binding.countries)
                setIfIsNotEmpty(movie.originalTitle, binding.originalTitle)
                if(movie.homepage == "") {
                    binding.homepage.visibility = View.GONE
                } else {
                    binding.homepage.text = "Домашняя страница: ${movie.homepage}"
                }
                isFavorite = movie.favorite
                isInWatchlist = movie.watchlist
                rating = movie.myRating
                binding.starBtn.visibility = View.VISIBLE
                binding.watchlistBtn.visibility = View.VISIBLE
                binding.loveBtn.visibility = View.VISIBLE
                setStates()
                if (movie.trailers.isNotEmpty()) {
                    binding.videoText.visibility = View.VISIBLE
                    binding.videoRecyclerview.visibility = View.VISIBLE
                    videoAdapter.setVideos(movie.trailers)
                }
                if (movie.recommendations.isNotEmpty()) {
                    binding.recommendationText.visibility = View.VISIBLE
                    binding.recommendationRecyclerview.visibility = View.VISIBLE
                    recommendationAdapter.setItems(movie.recommendations)
                }
                if (movie.similar.isNotEmpty()) {
                    binding.similarText.visibility = View.VISIBLE
                    binding.similarRecyclerview.visibility = View.VISIBLE
                    similarAdapter.setItems(movie.similar)
                }
                if (movie.cast.isNotEmpty()) {
                    binding.mainRolesTitle.visibility = View.VISIBLE
                    binding.mainRoles.visibility = View.VISIBLE
                    castAdapter.appendMovies(movie.cast)
                }
                if (movie.posters.isNotEmpty()) {
                    binding.posterText.visibility = View.VISIBLE
                    binding.posterRecyclerview.visibility = View.VISIBLE
                    posterAdapter.setImages(movie.posters)
                }
                if (movie.backdrops.isNotEmpty()) {
                    binding.backdropText.visibility = View.VISIBLE
                    binding.backdropRecyclerview.visibility = View.VISIBLE
                    backdropAdapter.setImages(movie.backdrops)
                }
            }
        }

        viewModel.addToWatchlistState.observe(viewLifecycleOwner) { state ->
            handleMarkedState(state, ::handleAddToWatchlistSuccess)
        }

        viewModel.markAsFavoriteState.observe(viewLifecycleOwner) { state ->
            handleMarkedState(state, ::handleMarkAsFavoriteSuccess)
        }

        viewModel.ratedState.observe(viewLifecycleOwner) { state ->
            when (state) {
                RatedState.Error -> {
                    binding.ratingProgress.hideAnimated()
                    showToast()
                }
                RatedState.Loading -> {
                    binding.ratingProgress.showAnimated()
                }
                is RatedState.Success -> {
                    binding.ratingProgress.hideAnimated()
                    handleRatedState(state.result)
                }
            }
        }
    }

    private fun handleMarkedState(
        state: MarkedState,
        onSuccess: (result: PostResponseStatus) -> Unit
    ) {
        when (state) {
            MarkedState.Error -> {
                binding.loading.visibility = View.GONE
                showToast()
            }
            MarkedState.Loading -> {
                binding.loading.visibility = View.VISIBLE
            }
            is MarkedState.Success -> {
                binding.loading.visibility = View.GONE
                onSuccess(state.result)
            }
        }
    }

    private fun handleAddToWatchlistSuccess(result: PostResponseStatus) {
        if (result.status == 1 || result.status == 12 || result.status == 13) {
            isInWatchlist = !isInWatchlist
        } else {
            showToast()
        }
        if (isInWatchlist) {
            binding.watchlistBtn.setBackgroundResource(R.drawable.ic_bookmark_marked)
        } else {
            binding.watchlistBtn.setBackgroundResource(R.drawable.ic_turned_in)
        }
    }

    private fun handleMarkAsFavoriteSuccess(result: PostResponseStatus) {
        if (result.status == 1 || result.status == 12 || result.status == 13) {
            isFavorite = !isFavorite
        } else {
            showToast()
        }
        if (isFavorite) {
            binding.loveBtn.setBackgroundResource(R.drawable.ic_favorite_marked)
        } else {
            binding.loveBtn.setBackgroundResource(R.drawable.ic_favorite)
        }
    }

    private fun handleRatedState(result: PostResponseStatus) {
        if (result.status == 1 || result.status == 12 || result.status == 13) {
            rating = binding.ratingBar.rating * 2
        } else {
            binding.ratingBar.rating = rating / 2
            showToast()
        }
        binding.starBtn.setBackgroundResource(R.drawable.ic_baseline_star_marked)
    }

    private fun showToast() {
        Toast.makeText(requireContext(), "Попробуйсте ещё раз", Toast.LENGTH_SHORT).show()
    }

    private fun handleLoadingAndError(state: FilmInfoStates) : Boolean {
        return when (state) {
            FilmInfoStates.Error -> {
                binding.loading.hideAnimated()
                binding.loaded.visibility = View.GONE
                binding.error.showAnimated()
                false
            }
            FilmInfoStates.Loading -> {
                binding.loading.showAnimated()
                binding.loaded.visibility = View.GONE
                binding.error.hideAnimated()
                false
            }
            is FilmInfoStates.Success -> {
                binding.loading.hideAnimated()
                binding.loaded.showAnimated()
                binding.error.visibility = View.GONE
                true
            }
        }
    }

    private fun initComponents() {
        initRecyclerViews()

        binding.loveBtn.setOnClickListener {
            viewModel.markAsFavorite(filmId!!, !isFavorite, USER.sessionKey)
        }

        binding.watchlistBtn.setOnClickListener {
            viewModel.addToWatchlist(filmId!!, !isInWatchlist, USER.sessionKey)
        }

        binding.starBtn.setOnClickListener {
            if (binding.ratingBar.visibility == View.VISIBLE) {
                binding.ratingBar.visibility = View.GONE
            } else {
                binding.ratingBar.visibility = View.VISIBLE
                binding.ratingBar.rating = rating / 2
            }
        }

        binding.ratingBar.setOnRatingBarChangeListener { _, rating, fromUser ->
            if (fromUser) {
                viewModel.rateMovie(filmId!!, USER.sessionKey, rating * 2)
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
            addItemDecoration(RegularDividerItemDecoration(8))
        }

        companiesAdapter = MovieRecyclerAdapter(this)
        val companiesLayoutManager = FlexboxLayoutManager(requireContext())
        companiesLayoutManager.justifyContent = JustifyContent.FLEX_START
        binding.companies.apply {
            layoutManager = companiesLayoutManager
            adapter = companiesAdapter
            addItemDecoration(RegularDividerItemDecoration(8))
        }

        posterAdapter = ImagesRecyclerAdapter(this)
        backdropAdapter = ImagesRecyclerAdapter(this)
        videoAdapter = VideoRecyclerAdapter()
        recommendationAdapter = RecommendationRecyclerAdapter(this)
        similarAdapter = RecommendationRecyclerAdapter(this)
        castAdapter = MovieRecyclerAdapter(this)
        initEpisodeRecyclerView()
        binding.apply {
            posterRecyclerview.setConfigHorizontalWithInnerAndOuterDivs(posterAdapter, requireContext(), 8, 48)
            backdropRecyclerview.setConfigHorizontalWithInnerAndOuterDivs(backdropAdapter, requireContext(), 24, 48)
            videoRecyclerview.setConfigHorizontalWithInnerAndOuterDivs(videoAdapter, requireContext(), 24, 48)
            recommendationRecyclerview.setConfigHorizontalWithInnerAndOuterDivs(recommendationAdapter, requireContext(), 24, 48)
            similarRecyclerview.setConfigHorizontalWithInnerAndOuterDivs(similarAdapter, requireContext(), 24, 48)
            mainRoles.setConfigHorizontalWithInnerAndOuterDivs(castAdapter, requireContext(), 24, 48)
        }
    }

    private fun formatRating(rating: Number?): String = rating?.let { "Оценка: $it" } ?: ""

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

    private fun initEpisodeRecyclerView() {
        episodeAdapter = EpisodeRecyclerAdapter(this)
        binding.posterRecyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = episodeAdapter
            addItemDecoration(RegularDividerItemDecoration(16))
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

    private fun setStates() {
        binding.apply {
            watchlistBtn.setCurrentResource({ isInWatchlist }, R.drawable.ic_bookmark_marked, R.drawable.ic_turned_in)
            loveBtn.setCurrentResource({ isFavorite }, R.drawable.ic_favorite_marked, R.drawable.ic_favorite)
            starBtn.setCurrentResource({ rating > 0.0 }, R.drawable.ic_baseline_star_marked, R.drawable.ic_star)
        }
    }

    override fun onOpenMovie(id: Long) {
        val action = FilmInfoFragmentDirections.actionFilmInfoFragmentSelf(id)
        view?.findNavController()?.navigate(action)
    }

    override fun onOpenTV(id: Long) {
        TODO("Not yet implemented")
    }

    override fun onOpenEpisode(tvId: Long, season: Int, episode: Int) {
        TODO("Not yet implemented")
    }


    override fun onOpenPerson(id: Long) {
        val action = FilmInfoFragmentDirections.actionFilmInfoFragmentToPersonInfoFragment(id)
        view?.findNavController()?.navigate(action)
    }

    override fun onOpenSeason(id: Long, season: Int) {
        TODO("Not yet implemented")
    }

    override fun onOpenCollection(id: Int) {
        val action = FilmInfoFragmentDirections.actionFilmInfoFragmentToCollectionInfoFragment(id)
        view?.findNavController()?.navigate(action)
    }

    override fun onOpenPicture(url: String) {
        val action = FilmInfoFragmentDirections.actionFilmInfoFragmentToPhotoFragment(url)
        view?.findNavController()?.navigate(action)
    }
}