package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.states.MainViewState
import com.example.myapplication.ui.activities.MainActivity.Companion.MOVIE_TYPE
import com.example.myapplication.ui.activities.MainActivity.Companion.PERSON_TYPE
import com.example.myapplication.ui.activities.MainActivity.Companion.TV_TYPE
import com.example.myapplication.ui.recyclerview.adapters.FeedRecyclerAdapter
import com.example.myapplication.ui.recyclerview.listeners.MoviePersonAndViewMoreClickListener
import com.example.myapplication.utils.setConfigHorizontalWithInnerAndOuterDivs
import com.example.myapplication.viewmodel.MainScreenRequest
import com.example.myapplication.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), MoviePersonAndViewMoreClickListener {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var filmsInTrendAdapter: FeedRecyclerAdapter
    private lateinit var filmsUpcomingAdapter: FeedRecyclerAdapter
    private lateinit var tvPopularAdapter: FeedRecyclerAdapter
    private lateinit var filmsNowPlayingAdapter: FeedRecyclerAdapter
    lateinit var tvBestAdapter: FeedRecyclerAdapter
    lateinit var tvNowOnAirAdapter: FeedRecyclerAdapter
    lateinit var tvTodayOnAirAdapter: FeedRecyclerAdapter
    lateinit var filmsBestAdapter: FeedRecyclerAdapter
    lateinit var personPopularAdapter: FeedRecyclerAdapter
    private var loaded: Int = 0

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadMoviesInTrend()
        viewModel.loadNowPlayingMovies()
        viewModel.loadUpcomingMovies()
        viewModel.loadPopularTV()
        viewModel.loadNowOnAirTV()
        viewModel.loadOnAirTodayTV()
        viewModel.loadTopRatedMovies()
        viewModel.loadTopRatedTV()
        viewModel.loadPopularPersons()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerViews()
        initUI()
        viewModel.moviesInTrend.observe(viewLifecycleOwner) { result ->
            when (result) {
                MainViewState.Error ->
                    showPopularMovies(result = false, progress = false, error = true)
                MainViewState.Loading ->
                    showPopularMovies(result = false, progress = true, error = false)
                is MainViewState.Success.FilmSuccess -> {
                    showPopularMovies(result = true, progress = false, error = false)
                    filmsInTrendAdapter.appendMovies(result.list)
                }
            }
        }

        viewModel.moviesNowPlaying.observe(viewLifecycleOwner) { result ->
            when (result) {
                MainViewState.Error ->
                    showNowPlayingMovies(result = false, progress = false, error = true)
                MainViewState.Loading ->
                    showNowPlayingMovies(result = false, progress = true, error = false)
                is MainViewState.Success.FilmSuccess -> {
                    showNowPlayingMovies(result = true, progress = false, error = false)
                    filmsNowPlayingAdapter.appendMovies(result.list)
                }
            }
        }

        viewModel.moviesUpcoming.observe(viewLifecycleOwner) { result ->
            when (result) {
                MainViewState.Error ->
                    showUpcomingMovies(result = false, progress = false, error = true)
                MainViewState.Loading ->
                    showUpcomingMovies(result = false, progress = true, error = false)
                is MainViewState.Success.FilmSuccess -> {
                    showUpcomingMovies(result = true, progress = false, error = false)
                    filmsUpcomingAdapter.appendMovies(result.list)
                }
            }
        }

        viewModel.popularTV.observe(viewLifecycleOwner) { result ->
            when (result) {
                MainViewState.Error ->
                    showPopularTvs(result = false, progress = false, error = true)
                MainViewState.Loading ->
                    showPopularTvs(result = false, progress = true, error = false)
                is MainViewState.Success.TvSuccess -> {
                    showPopularTvs(result = true, progress = false, error = false)
                    tvPopularAdapter.appendMovies(result.list)
                }
            }
        }

        viewModel.topRatedTV.observe(viewLifecycleOwner) { result ->
            when (result) {
                MainViewState.Error ->
                    showTopRatedTvs(result = false, progress = false, error = true)
                MainViewState.Loading ->
                    showTopRatedTvs(result = false, progress = true, error = false)
                is MainViewState.Success.TvSuccess -> {
                    showTopRatedTvs(result = true, progress = false, error = false)
                    tvBestAdapter.appendMovies(result.list)
                }
            }
        }

        viewModel.nowOnAirTV.observe(viewLifecycleOwner) { result ->
            when (result) {
                MainViewState.Error ->
                    showNowOnAirTvs(result = false, progress = false, error = true)
                MainViewState.Loading ->
                    showNowOnAirTvs(result = false, progress = true, error = false)
                is MainViewState.Success.TvSuccess -> {
                    showNowOnAirTvs(result = true, progress = false, error = false)
                    tvNowOnAirAdapter.appendMovies(result.list)
                }
            }
        }

        viewModel.onAirTodayTV.observe(viewLifecycleOwner) { result ->
            when (result) {
                MainViewState.Error ->
                    showOnAirTodayTvs(result = false, progress = false, error = true)
                MainViewState.Loading ->
                    showOnAirTodayTvs(result = false, progress = true, error = false)
                is MainViewState.Success.TvSuccess -> {
                    showOnAirTodayTvs(result = true, progress = false, error = false)
                    tvTodayOnAirAdapter.appendMovies(result.list)
                }
            }
        }

        viewModel.moviesTopRated.observe(viewLifecycleOwner) { result ->
            when (result) {
                MainViewState.Error ->
                    showTopRatedMovies(result = false, progress = false, error = true)
                MainViewState.Loading ->
                    showTopRatedMovies(result = false, progress = true, error = false)
                is MainViewState.Success.FilmSuccess -> {
                    showTopRatedMovies(result = true, progress = false, error = false)
                    filmsBestAdapter.appendMovies(result.list)
                }
            }
        }

        viewModel.popularPersons.observe(viewLifecycleOwner) { result ->
            when (result) {
                MainViewState.Error ->
                    showPopularPersons(result = false, progress = false, error = true)
                MainViewState.Loading ->
                    showPopularPersons(result = false, progress = true, error = false)
                is MainViewState.Success.PersonSuccess -> {
                    showPopularPersons(result = true, progress = false, error = false)
                    personPopularAdapter.appendMovies(result.list)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUI() {
        binding.apply {
            popularMoviesError.errorButton.setOnClickListener { viewModel.loadMoviesInTrend() }
            upcomingMoviesError.errorButton.setOnClickListener { viewModel.loadUpcomingMovies() }
            popularTvError.errorButton.setOnClickListener { viewModel.loadPopularTV() }
            nowPlayingMoviesError.errorButton.setOnClickListener { viewModel.loadNowPlayingMovies() }
            topRatedTvError.errorButton.setOnClickListener { viewModel.loadTopRatedTV() }
            nowOnAirTvError.errorButton.setOnClickListener { viewModel.loadNowOnAirTV() }
            onAirTodayTvError.errorButton.setOnClickListener { viewModel.loadOnAirTodayTV() }
            topRatedMoviesError.errorButton.setOnClickListener { viewModel.loadTopRatedMovies() }
            popularPersonsError.errorButton.setOnClickListener { viewModel.loadPopularPersons() }
        }
    }

    private fun initRecyclerViews() {
        filmsInTrendAdapter = FeedRecyclerAdapter(this, MainScreenRequest.POPULAR_MOVIES)
        binding.popularMovies.setConfigHorizontalWithInnerAndOuterDivs(filmsInTrendAdapter, requireContext(), INNER_DIV, OUTER_DIV)

        filmsUpcomingAdapter = FeedRecyclerAdapter(this, MainScreenRequest.UPCOMING_MOVIES)
        binding.upcomingMovies.setConfigHorizontalWithInnerAndOuterDivs(filmsUpcomingAdapter, requireContext(), INNER_DIV, OUTER_DIV)

        tvPopularAdapter = FeedRecyclerAdapter(this, MainScreenRequest.POPULAR_TVS)
        binding.popularTv.setConfigHorizontalWithInnerAndOuterDivs(tvPopularAdapter, requireContext(), INNER_DIV, OUTER_DIV)

        filmsNowPlayingAdapter = FeedRecyclerAdapter(this, MainScreenRequest.NOW_PLAYING_MOVIES)
        binding.nowPlayingMovies.setConfigHorizontalWithInnerAndOuterDivs(filmsNowPlayingAdapter, requireContext(), INNER_DIV, OUTER_DIV)

        tvBestAdapter = FeedRecyclerAdapter(this, MainScreenRequest.TOP_RATED_TVS)
        binding.topRatedTv.setConfigHorizontalWithInnerAndOuterDivs(tvBestAdapter, requireContext(), INNER_DIV, OUTER_DIV)

        tvNowOnAirAdapter = FeedRecyclerAdapter(this, MainScreenRequest.ON_THE_AIR_TVS)
        binding.nowOnAirTv.setConfigHorizontalWithInnerAndOuterDivs(tvNowOnAirAdapter, requireContext(), INNER_DIV, OUTER_DIV)

        tvTodayOnAirAdapter = FeedRecyclerAdapter(this, MainScreenRequest.AIRING_TODAY_TVS)
        binding.onAirTodayTv.setConfigHorizontalWithInnerAndOuterDivs(tvTodayOnAirAdapter, requireContext(), INNER_DIV, OUTER_DIV)

        filmsBestAdapter = FeedRecyclerAdapter(this, MainScreenRequest.TOP_RATED_MOVIES)
        binding.topRatedMovies.setConfigHorizontalWithInnerAndOuterDivs(filmsBestAdapter, requireContext(), INNER_DIV, OUTER_DIV)

        personPopularAdapter = FeedRecyclerAdapter(this, MainScreenRequest.POPULAR_PERSONS)
        binding.popularPersons.setConfigHorizontalWithInnerAndOuterDivs(personPopularAdapter, requireContext(), INNER_DIV, OUTER_DIV)
    }

    override fun onOpenMovie(id: Long) {
        val action = MainFragmentDirections.actionMainPageToItemInfoFragment(id, MOVIE_TYPE, 0, 0)
        view?.findNavController()?.navigate(action)
    }

    override fun onOpenTV(id: Long) {
        val action = MainFragmentDirections.actionMainPageToItemInfoFragment(id, TV_TYPE, 0, 0)
        view?.findNavController()?.navigate(action)
    }

    override fun onOpenPerson(id: Long) {
        val action = MainFragmentDirections.actionMainPageToItemInfoFragment(id, PERSON_TYPE, 0, 0)
        view?.findNavController()?.navigate(action)
    }

    override fun onOpenMore(requestType: MainScreenRequest) {
        val action = MainFragmentDirections.actionMainPageToTopListFragment(requestType)
        view?.findNavController()?.navigate(action)
    }

    private fun showPopularMovies(result: Boolean, progress: Boolean, error: Boolean) {
        showView(binding.popularMovies, result)
        showView(binding.popularMoviesProgress, progress)
        showView(binding.popularMoviesError.errorView, error)
    }

    private fun showPopularTvs(result: Boolean, progress: Boolean, error: Boolean) {
        showView(binding.popularTv, result)
        showView(binding.popularTvProgress, progress)
        showView(binding.popularTvError.errorView, error)
    }

    private fun showTopRatedTvs(result: Boolean, progress: Boolean, error: Boolean) {
        showView(binding.topRatedTv, result)
        showView(binding.topRatedTvProgress, progress)
        showView(binding.topRatedTvError.errorView, error)
    }

    private fun showNowOnAirTvs(result: Boolean, progress: Boolean, error: Boolean) {
        showView(binding.nowOnAirTv, result)
        showView(binding.nowOnAirTvProgress, progress)
        showView(binding.nowOnAirTvError.errorView, error)
    }

    private fun showOnAirTodayTvs(result: Boolean, progress: Boolean, error: Boolean) {
        showView(binding.onAirTodayTv, result)
        showView(binding.onAirTodayTvProgress, progress)
        showView(binding.onAirTodayTvError.errorView, error)
    }

    private fun showNowPlayingMovies(result: Boolean, progress: Boolean, error: Boolean) {
        showView(binding.nowPlayingMovies, result)
        showView(binding.nowPlayingMoviesProgress, progress)
        showView(binding.nowPlayingMoviesError.errorView, error)
    }

    private fun showUpcomingMovies(result: Boolean, progress: Boolean, error: Boolean) {
        showView(binding.upcomingMovies, result)
        showView(binding.upcomingMoviesProgress, progress)
        showView(binding.upcomingMoviesError.errorView, error)
    }

    private fun showTopRatedMovies(result: Boolean, progress: Boolean, error: Boolean) {
        showView(binding.topRatedMovies, result)
        showView(binding.topRatedMoviesProgress, progress)
        showView(binding.topRatedMoviesError.errorView, error)
    }

    private fun showPopularPersons(result: Boolean, progress: Boolean, error: Boolean) {
        showView(binding.popularPersons, result)
        showView(binding.popularPersonsProgress, progress)
        showView(binding.popularPersonsError.errorView, error)
    }

    private fun showView(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    companion object {
        const val INNER_DIV = 24
        const val OUTER_DIV = 48
    }
}