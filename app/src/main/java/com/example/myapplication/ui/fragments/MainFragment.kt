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
import com.example.myapplication.ui.recyclerview.adapters.FeedRecyclerAdapter
import com.example.myapplication.ui.recyclerview.listeners.MoviePersonAndViewMoreAndTvClickListener
import com.example.myapplication.utils.hideAnimated
import com.example.myapplication.utils.setConfigHorizontalWithInnerAndOuterDivs
import com.example.myapplication.utils.showAnimated
import com.example.myapplication.viewmodel.MainScreenRequest
import com.example.myapplication.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), MoviePersonAndViewMoreAndTvClickListener {
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
                MainViewState.Error -> {
                    binding.popularMovies.visibility = View.GONE
                    binding.popularMoviesProgress.hideAnimated()
                    binding.popularMoviesError.errorView.showAnimated()
                }
                MainViewState.Loading -> {
                    binding.popularMovies.visibility = View.GONE
                    binding.popularMoviesProgress.showAnimated()
                    binding.popularMoviesError.errorView.hideAnimated()
                }
                is MainViewState.Success.FilmSuccess -> {
                    binding.popularMovies.showAnimated()
                    binding.popularMoviesProgress.hideAnimated()
                    binding.popularMoviesError.errorView.visibility = View.GONE
                    filmsInTrendAdapter.appendMovies(result.list)
                }
            }
        }

        viewModel.moviesNowPlaying.observe(viewLifecycleOwner) { result ->
            when (result) {
                MainViewState.Error -> {
                    binding.nowPlayingMovies.visibility = View.GONE
                    binding.nowPlayingMoviesProgress.hideAnimated()
                    binding.nowPlayingMoviesError.errorView.showAnimated()
                }
                MainViewState.Loading -> {
                    binding.nowPlayingMovies.visibility = View.GONE
                    binding.nowPlayingMoviesProgress.showAnimated()
                    binding.nowPlayingMoviesError.errorView.hideAnimated()
                }
                is MainViewState.Success.FilmSuccess -> {
                    binding.nowPlayingMovies.showAnimated()
                    binding.nowPlayingMoviesProgress.hideAnimated()
                    binding.nowPlayingMoviesError.errorView.visibility = View.GONE
                    filmsNowPlayingAdapter.appendMovies(result.list)
                }
            }
        }

        viewModel.moviesUpcoming.observe(viewLifecycleOwner) { result ->
            when (result) {
                MainViewState.Error -> {
                    binding.upcomingMovies.visibility = View.GONE
                    binding.upcomingMoviesProgress.hideAnimated()
                    binding.upcomingMoviesError.errorView.showAnimated()
                }
                MainViewState.Loading -> {
                    binding.upcomingMovies.visibility = View.GONE
                    binding.upcomingMoviesProgress.showAnimated()
                    binding.upcomingMoviesError.errorView.hideAnimated()
                }
                is MainViewState.Success.FilmSuccess -> {
                    binding.upcomingMovies.showAnimated()
                    binding.upcomingMoviesProgress.hideAnimated()
                    binding.upcomingMoviesError.errorView.visibility = View.GONE
                    filmsUpcomingAdapter.appendMovies(result.list)
                }
            }
        }

        viewModel.popularTV.observe(viewLifecycleOwner) { result ->
            when (result) {
                MainViewState.Error -> {
                    binding.popularTv.visibility = View.GONE
                    binding.popularTvProgress.hideAnimated()
                    binding.popularTvError.errorView.showAnimated()
                }
                MainViewState.Loading -> {
                    binding.popularTv.visibility = View.GONE
                    binding.popularTvProgress.showAnimated()
                    binding.popularTvError.errorView.hideAnimated()
                }
                is MainViewState.Success.TvSuccess -> {
                    binding.popularTv.showAnimated()
                    binding.popularTvProgress.hideAnimated()
                    binding.popularTvError.errorView.visibility = View.GONE
                    tvPopularAdapter.appendMovies(result.list)
                }
            }
        }

        viewModel.topRatedTV.observe(viewLifecycleOwner) { result ->
            when (result) {
                MainViewState.Error -> {
                    binding.topRatedTv.visibility = View.GONE
                    binding.topRatedTvProgress.hideAnimated()
                    binding.topRatedTvError.errorView.showAnimated()
                }
                MainViewState.Loading -> {
                    binding.topRatedTv.visibility = View.GONE
                    binding.topRatedTvProgress.showAnimated()
                    binding.topRatedTvError.errorView.hideAnimated()
                }
                is MainViewState.Success.TvSuccess -> {
                    binding.topRatedTv.showAnimated()
                    binding.topRatedTvProgress.hideAnimated()
                    binding.topRatedTvError.errorView.visibility = View.GONE
                    tvBestAdapter.appendMovies(result.list)
                }
            }
        }

        viewModel.nowOnAirTV.observe(viewLifecycleOwner) { result ->
            when (result) {
                MainViewState.Error -> {
                    binding.nowOnAirTv.visibility = View.GONE
                    binding.nowOnAirTvProgress.hideAnimated()
                    binding.nowOnAirTvError.errorView.showAnimated()
                }
                MainViewState.Loading -> {
                    binding.nowOnAirTv.visibility = View.GONE
                    binding.nowOnAirTvProgress.showAnimated()
                    binding.nowOnAirTvError.errorView.hideAnimated()
                }
                is MainViewState.Success.TvSuccess -> {
                    binding.nowOnAirTv.showAnimated()
                    binding.nowOnAirTvProgress.hideAnimated()
                    binding.nowOnAirTvError.errorView.visibility = View.GONE
                    tvNowOnAirAdapter.appendMovies(result.list)
                }
            }
        }

        viewModel.onAirTodayTV.observe(viewLifecycleOwner) { result ->
            when (result) {
                MainViewState.Error -> {
                    binding.onAirTodayTv.visibility = View.GONE
                    binding.onAirTodayTvProgress.hideAnimated()
                    binding.onAirTodayTvError.errorView.showAnimated()
                }
                MainViewState.Loading -> {
                    binding.onAirTodayTv.visibility = View.GONE
                    binding.onAirTodayTvProgress.showAnimated()
                    binding.onAirTodayTvError.errorView.hideAnimated()
                }
                is MainViewState.Success.TvSuccess -> {
                    binding.onAirTodayTv.showAnimated()
                    binding.onAirTodayTvProgress.hideAnimated()
                    binding.onAirTodayTvError.errorView.visibility = View.GONE
                    tvTodayOnAirAdapter.appendMovies(result.list)
                }
            }
        }

        viewModel.moviesTopRated.observe(viewLifecycleOwner) { result ->
            when (result) {
                MainViewState.Error -> {
                    binding.topRatedMovies.visibility = View.GONE
                    binding.topRatedMoviesProgress.hideAnimated()
                    binding.topRatedMoviesError.errorView.showAnimated()
                }
                MainViewState.Loading -> {
                    binding.topRatedMovies.visibility = View.GONE
                    binding.topRatedMoviesProgress.showAnimated()
                    binding.topRatedMoviesError.errorView.hideAnimated()
                }
                is MainViewState.Success.FilmSuccess -> {
                    binding.topRatedMovies.showAnimated()
                    binding.topRatedMoviesProgress.hideAnimated()
                    binding.topRatedMoviesError.errorView.visibility = View.GONE
                    filmsBestAdapter.appendMovies(result.list)
                }
            }
        }

        viewModel.popularPersons.observe(viewLifecycleOwner) { result ->
            when (result) {
                MainViewState.Error ->{
                    binding.popularPersons.visibility = View.GONE
                    binding.popularPersonsProgress.hideAnimated()
                    binding.popularPersonsError.errorView.showAnimated()
                }
                MainViewState.Loading -> {
                    binding.popularPersons.visibility = View.GONE
                    binding.popularPersonsProgress.showAnimated()
                    binding.popularPersonsError.errorView.hideAnimated()
                }
                is MainViewState.Success.PersonSuccess -> {
                    binding.popularPersons.showAnimated()
                    binding.popularPersonsProgress.hideAnimated()
                    binding.popularPersonsError.errorView.visibility = View.GONE
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
        val action = MainFragmentDirections.actionMainPageToFilmInfoFragment(id)
        view?.findNavController()?.navigate(action)
    }

    override fun onOpenTV(id: Long) {
        val action = MainFragmentDirections.actionMainPageToTvInfoFragment(id)
        view?.findNavController()?.navigate(action)
    }

    override fun onOpenPerson(id: Long) {
        val action = MainFragmentDirections.actionMainPageToPersonInfoFragment(id)
        view?.findNavController()?.navigate(action)
    }

    override fun onOpenMore(requestType: MainScreenRequest) {
        val action = MainFragmentDirections.actionMainPageToTopListFragment(requestType)
        view?.findNavController()?.navigate(action)
    }

    companion object {
        const val INNER_DIV = 24
        const val OUTER_DIV = 48
    }
}