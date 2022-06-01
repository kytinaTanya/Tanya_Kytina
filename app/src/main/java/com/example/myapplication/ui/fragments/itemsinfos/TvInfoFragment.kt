package com.example.myapplication.ui.fragments.itemsinfos

import android.os.Bundle
import android.util.Log
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
import com.example.myapplication.databinding.FragmentTvInfoBinding
import com.example.myapplication.firebase.AUTH
import com.example.myapplication.firebase.USER
import com.example.myapplication.models.history.PostResponseStatus
import com.example.myapplication.models.pojo.ProductionCounties
import com.example.myapplication.models.pojo.TvProducer
import com.example.myapplication.states.MarkedState
import com.example.myapplication.states.RatedState
import com.example.myapplication.states.TvInfoState
import com.example.myapplication.ui.activities.MainActivity.Companion.MEDIA_ID
import com.example.myapplication.ui.recyclerview.RegularDividerItemDecoration
import com.example.myapplication.ui.recyclerview.adapters.*
import com.example.myapplication.ui.recyclerview.listeners.AllSpecificListenerAndTv
import com.example.myapplication.ui.recyclerview.listeners.PhotoClickListener
import com.example.myapplication.utils.*
import com.example.myapplication.utils.Utils.Companion.formatDate
import com.example.myapplication.utils.Utils.Companion.setIfIsNotEmpty
import com.example.myapplication.viewmodel.itemsinfos.TvInfoViewModel
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

@AndroidEntryPoint
class TvInfoFragment : Fragment(), AllSpecificListenerAndTv, PhotoClickListener {

    private var _binding: FragmentTvInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TvInfoViewModel by viewModels()
    private var currentUser: FirebaseUser? = null
    private var isFavorite: Boolean = false
    private var isInWatchlist: Boolean = false
    private var rating: Float = 0.0F
    private var currentRating = rating
    private val format = DecimalFormat("#,###.##")
    private var tvId: Long = 0L

    //Адаптеры для всех recyclerView
    private lateinit var genresAdapter: GenresRecyclerAdapter
    private lateinit var posterAdapter: ImagesRecyclerAdapter
    private lateinit var backdropAdapter: ImagesRecyclerAdapter
    private lateinit var videoAdapter: VideoRecyclerAdapter
    private lateinit var recommendationAdapter: RecommendationRecyclerAdapter
    private lateinit var similarAdapter: RecommendationRecyclerAdapter
    private lateinit var companiesAdapter: MovieRecyclerAdapter
    private lateinit var seasonAdapter: MovieRecyclerAdapter
    private lateinit var episodeAdapter: EpisodeRecyclerAdapter
    private lateinit var castAdapter: MovieRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentUser = AUTH.currentUser
        arguments?.let {
            tvId = it.getLong(MEDIA_ID)
        }
        viewModel.loadData(tvId, USER.sessionKey)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTvInfoBinding.inflate(inflater, container, false)
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
        binding.errorButton.setOnClickListener { viewModel.loadData(tvId, USER.sessionKey) }
        viewModel.tvInfo.observe(viewLifecycleOwner) { state ->
            if (handleLoadingAndError(state)) {
                val tv = (state as TvInfoState.Success).data
                binding.mainImage.apply {
                    visibility = View.VISIBLE
                    setImage(BuildConfig.BASE_POSTER_URL + tv.posterPath)
                    setOnClickListener { onOpenPicture(BuildConfig.BASE_POSTER_URL + tv.posterPath) }
                }
                binding.toolbar.title = tv.name
                binding.toolbar.setNavigationOnClickListener {
                    view?.findNavController()?.popBackStack()
                }
                setIfIsNotEmpty(tv.name, binding.movieTitle)
                setIfIsNotEmpty(formatDate(tv.firstAirDate), binding.yearOfMovie)
                setIfIsNotEmpty(tv.overview, binding.movieAnnotation)
                setIfIsNotEmpty(tv.tagline, binding.tagline)
                setIfIsNotEmpty(formatRating(rating), binding.movieRating)
                setIfIsNotEmpty("Количество сезонов: ${tv.numOfSeasons}", binding.numberOfSeasons)
                setIfIsNotEmpty("Количество серий: ${tv.episodes}", binding.numberOfEpisodes)
                if (tv.genres.isEmpty()) {
                    binding.genres.visibility = View.GONE
                } else {
                    genresAdapter.setGenres(tv.genres)
                }

                if (tv.lastEpisode == null) {
                    binding.collection.visibility = View.GONE
                } else {
                    if (tv.lastEpisode.stillPath == "null" || tv.lastEpisode.name == "") {
                        binding.collection.visibility = View.GONE
                    } else {
                        initCollectionWidget(
                            "Последний эпизод",
                            "${BuildConfig.BASE_STILL_URL}${tv.lastEpisode.stillPath}",
                            tv.lastEpisode.name)
                        binding.collection.setOnClickListener {
                            Log.d("LAST EPISODE",
                                "${tv.lastEpisode.showId} ${tv.lastEpisode.seasonNum} ${tv.lastEpisode.episodeNum}")
                            onOpenEpisode(tv.lastEpisode.showId,
                                tv.lastEpisode.seasonNum,
                                tv.lastEpisode.episodeNum)

                        }
                    }
                }

                if (tv.companies.isEmpty()) {
                    binding.companies.visibility = View.GONE
                } else {
                    companiesAdapter.appendMovies(tv.companies)
                }
                setIfIsNotEmpty(createCountriesList(tv.countries), binding.countries)
                setIfIsNotEmpty(tv.originalName, binding.originalTitle)
                if (tv.homepage == "") {
                    binding.homepage.visibility = View.GONE
                } else {
                    binding.homepage.text = "Домашняя страница: ${tv.homepage}"
                }

                setIfIsNotEmpty(createCreatedByList(tv.createdBy), binding.createdBy)
                if (tv.seasons.isEmpty()) {
                    binding.seasons.visibility = View.GONE
                } else {
                    seasonAdapter.appendMovies(tv.seasons)
                }
                if (binding.createdBy.visibility == View.GONE &&
                    binding.countries.visibility == View.GONE &&
                    binding.companies.visibility == View.GONE
                ) {
                    binding.companyTitle.visibility = View.GONE
                }
                binding.createdBy.visibility = View.GONE
                isFavorite = tv.favorite
                isInWatchlist = tv.watchlist
                rating = tv.myRating
                binding.starBtn.visibility = View.VISIBLE
                binding.watchlistBtn.visibility = View.VISIBLE
                binding.loveBtn.visibility = View.VISIBLE
                setStates()
                if (tv.videos.isNotEmpty()) {
                    binding.videoText.visibility = View.VISIBLE
                    binding.videoRecyclerview.visibility = View.VISIBLE
                    videoAdapter.setVideos(tv.videos)
                }
                if (tv.recommendations.isNotEmpty()) {
                    binding.recommendationText.visibility = View.VISIBLE
                    binding.recommendationRecyclerview.visibility = View.VISIBLE
                    recommendationAdapter.setItems(tv.recommendations)
                }
                if (tv.similar.isNotEmpty()) {
                    binding.similarText.visibility = View.VISIBLE
                    binding.similarRecyclerview.visibility = View.VISIBLE
                    similarAdapter.setItems(tv.similar)
                }
                if (tv.cast.isNotEmpty()) {
                    binding.mainRolesTitle.visibility = View.VISIBLE
                    binding.mainRoles.visibility = View.VISIBLE
                    castAdapter.appendMovies(tv.cast)
                }
                if (tv.posters.isNotEmpty()) {
                    binding.posterText.visibility = View.VISIBLE
                    binding.posterRecyclerview.visibility = View.VISIBLE
                    posterAdapter.setImages(tv.posters)
                }
                if (tv.backdrops.isNotEmpty()) {
                    binding.backdropText.visibility = View.VISIBLE
                    binding.backdropRecyclerview.visibility = View.VISIBLE
                    backdropAdapter.setImages(tv.backdrops)
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
                    binding.loading.visibility = View.GONE
                    showToast()
                }
                RatedState.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                }
                is RatedState.Success -> {
                    binding.loading.visibility = View.GONE
                    handleRatedState(state.result)
                }
            }
        }
    }

    private fun handleLoadingAndError(state: TvInfoState): Boolean {
        return when (state) {
            TvInfoState.Error -> {
                binding.loading.hideAnimated()
                binding.loaded.visibility = View.GONE
                binding.error.showAnimated()
                false
            }
            TvInfoState.Loading -> {
                binding.loading.showAnimated()
                binding.loaded.visibility = View.GONE
                binding.error.hideAnimated()
                false
            }
            is TvInfoState.Success -> {
                binding.loading.hideAnimated()
                binding.loaded.showAnimated()
                binding.error.visibility = View.GONE
                true
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
            rating = currentRating
        } else {
            currentRating = rating
            showToast()
        }
        binding.starBtn.setBackgroundResource(R.drawable.ic_baseline_star_marked)
        binding.ratingBar.rating = rating / 2
    }

    private fun showToast() {
        Toast.makeText(requireContext(), "Попробуйсте ещё раз", Toast.LENGTH_SHORT).show()
    }

    private fun initComponents() {
        initRecyclerViews()

        binding.loveBtn.setOnClickListener {
            viewModel.markAsFavorite(tvId, !isFavorite, USER.sessionKey)
        }

        binding.watchlistBtn.setOnClickListener {
            viewModel.addToWatchlist(tvId, !isInWatchlist, USER.sessionKey)
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
                viewModel.rateTv(tvId, USER.sessionKey, rating * 2)
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
        seasonAdapter = MovieRecyclerAdapter(this)
        castAdapter = MovieRecyclerAdapter(this)
        initEpisodeRecyclerView()
        binding.apply {
            posterRecyclerview.setConfigHorizontalWithInnerAndOuterDivs(posterAdapter,
                requireContext(),
                24,
                48)
            backdropRecyclerview.setConfigHorizontalWithInnerAndOuterDivs(backdropAdapter,
                requireContext(),
                24,
                48)
            videoRecyclerview.setConfigHorizontalWithInnerAndOuterDivs(videoAdapter,
                requireContext(),
                24,
                48)
            recommendationRecyclerview.setConfigHorizontalWithInnerAndOuterDivs(
                recommendationAdapter,
                requireContext(),
                24,
                48)
            similarRecyclerview.setConfigHorizontalWithInnerAndOuterDivs(similarAdapter,
                requireContext(),
                24,
                48)
            seasons.setConfigHorizontalWithInnerAndOuterDivs(seasonAdapter,
                requireContext(),
                24,
                48)
            mainRoles.setConfigHorizontalWithInnerAndOuterDivs(castAdapter,
                requireContext(),
                24,
                48)
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

    private fun formatRating(rating: Number?): String = rating?.let { "Оценка: $it" } ?: ""

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
        if (str == "") {
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
        return if (str == "") {
            str
        } else {
            "Страны производства: $str"
        }
    }

    private fun setStates() {
        binding.apply {
            if (isInWatchlist != null && isFavorite != null) {
                watchlistBtn.setCurrentResource({ isInWatchlist },
                    R.drawable.ic_bookmark_marked,
                    R.drawable.ic_turned_in)
                loveBtn.setCurrentResource({ isFavorite },
                    R.drawable.ic_favorite_marked,
                    R.drawable.ic_favorite)
            }
            starBtn.setCurrentResource({ rating > 0.0 },
                R.drawable.ic_baseline_star_marked,
                R.drawable.ic_star)
        }
    }

    override fun onOpenMovie(id: Long) {
        TODO("Not yet implemented")
    }

    override fun onOpenTV(id: Long) {
        val action = TvInfoFragmentDirections.actionTvInfoFragmentSelf(id)
        view?.findNavController()?.navigate(action)
    }

    override fun onOpenPerson(id: Long) {
        val action = TvInfoFragmentDirections.actionTvInfoFragmentToPersonInfoFragment(id)
        view?.findNavController()?.navigate(action)
    }

    override fun onOpenSeason(id: Long, season: Int) {
        val action = TvInfoFragmentDirections.actionTvInfoFragmentToSeasonInfoFragment(id, season)
        view?.findNavController()?.navigate(action)
    }

    override fun onOpenCollection(id: Int) {
        TODO("Not yet implemented")
    }

    override fun onOpenEpisode(tvId: Long, seasonNum: Int, episode: Int) {
        val action = TvInfoFragmentDirections.actionTvInfoFragmentToEpisodeInfoFragment(
            tvId,
            seasonNum,
            episode
        )
        view?.findNavController()?.navigate(action)
    }

    override fun onOpenPicture(url: String) {
        val action = TvInfoFragmentDirections.actionTvInfoFragmentToPhotoFragment(url)
        view?.findNavController()?.navigate(action)
    }
}