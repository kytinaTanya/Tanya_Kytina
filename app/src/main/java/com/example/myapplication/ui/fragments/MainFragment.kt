package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.ui.activities.MainActivity.Companion.MOVIE_TYPE
import com.example.myapplication.ui.activities.MainActivity.Companion.PERSON_TYPE
import com.example.myapplication.ui.activities.MainActivity.Companion.TV_TYPE
import com.example.myapplication.ui.recyclerview.adapters.FeedRecyclerAdapter
import com.example.myapplication.ui.recyclerview.listeners.MovieAndPersonListener
import com.example.myapplication.utils.setConfigHorizontalLinearWithDiv
import com.example.myapplication.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), MovieAndPersonListener {
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
        super.onViewCreated(view, savedInstanceState)
        initRecyclerViews()
    }

    override fun onResume() {
        super.onResume()

        viewModel.moviesInTrend.observe(this) { movies ->
            filmsInTrendAdapter.appendMovies(movies)
        }

        viewModel.moviesNowPlaying.observe(this) { movies ->
            filmsNowPlayingAdapter.appendMovies(movies)
        }

        viewModel.moviesUpcoming.observe(this) { movies ->
            filmsUpcomingAdapter.appendMovies(movies)
        }

        viewModel.popularTV.observe(this) { tv ->
            tvPopularAdapter.appendMovies(tv)
        }

        viewModel.topRatedTV.observe(this) { tv ->
            tvBestAdapter.appendMovies(tv)
        }

        viewModel.nowOnAirTV.observe(this) { tv ->
            tvNowOnAirAdapter.appendMovies(tv)
        }

        viewModel.onAirTodayTV.observe(this) { tv ->
            tvTodayOnAirAdapter.appendMovies(tv)
        }

        viewModel.moviesTopRated.observe(this) { movies ->
            filmsBestAdapter.appendMovies(movies)
        }

        viewModel.popularPersons.observe(this) { persons ->
            personPopularAdapter.appendMovies(persons)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerViews() {
        filmsInTrendAdapter = FeedRecyclerAdapter(this)
        binding.popularMovies.setConfigHorizontalLinearWithDiv(filmsInTrendAdapter, requireContext(), 16)

        filmsUpcomingAdapter = FeedRecyclerAdapter(this)
        binding.upcomingMovies.setConfigHorizontalLinearWithDiv(filmsUpcomingAdapter, requireContext(), 16)

        tvPopularAdapter = FeedRecyclerAdapter(this)
        binding.popularTv.setConfigHorizontalLinearWithDiv(tvPopularAdapter, requireContext(), 16)

        filmsNowPlayingAdapter = FeedRecyclerAdapter(this)
        binding.nowPlayingMovies.setConfigHorizontalLinearWithDiv(filmsNowPlayingAdapter, requireContext(), 16)

        tvBestAdapter = FeedRecyclerAdapter(this)
        binding.topRatedTv.setConfigHorizontalLinearWithDiv(tvBestAdapter, requireContext(), 16)

        tvNowOnAirAdapter = FeedRecyclerAdapter(this)
        binding.nowOnAirTv.setConfigHorizontalLinearWithDiv(tvNowOnAirAdapter, requireContext(), 16)

        tvTodayOnAirAdapter = FeedRecyclerAdapter(this)
        binding.onAirTodayTv.setConfigHorizontalLinearWithDiv(tvTodayOnAirAdapter, requireContext(), 16)

        filmsBestAdapter = FeedRecyclerAdapter(this)
        binding.topRatedMovies.setConfigHorizontalLinearWithDiv(filmsBestAdapter, requireContext(), 16)

        personPopularAdapter = FeedRecyclerAdapter(this)
        binding.popularPersons.setConfigHorizontalLinearWithDiv(personPopularAdapter, requireContext(), 16)
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
}