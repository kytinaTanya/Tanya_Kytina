package com.example.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.ui.recyclerview.adapters.MovieClickListener
import com.example.myapplication.ui.recyclerview.adapters.MovieRecyclerAdapter
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.ui.recyclerview.DividerItemDecoration
import com.example.myapplication.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), MovieClickListener {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var filmsInTrendAdapter: MovieRecyclerAdapter
    private lateinit var filmsUpcomingAdapter: MovieRecyclerAdapter
    private lateinit var tvPopularAdapter: MovieRecyclerAdapter
    private lateinit var filmsNowPlayingAdapter: MovieRecyclerAdapter
    lateinit var tvBestAdapter: MovieRecyclerAdapter
    lateinit var tvNowOnAirAdapter: MovieRecyclerAdapter
    lateinit var tvTodayOnAirAdapter: MovieRecyclerAdapter
    lateinit var filmsBestAdapter: MovieRecyclerAdapter
    lateinit var personPopularAdapter: MovieRecyclerAdapter

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
        filmsInTrendAdapter = MovieRecyclerAdapter(this)
        binding.popularMovies.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false)
            adapter = filmsInTrendAdapter
            addItemDecoration(DividerItemDecoration(16))
        }

        filmsUpcomingAdapter = MovieRecyclerAdapter(this)
        binding.upcomingMovies.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false)
            adapter = filmsUpcomingAdapter
            addItemDecoration(DividerItemDecoration(16))
        }

        tvPopularAdapter = MovieRecyclerAdapter(this)
        binding.popularTv.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false)
            adapter = tvPopularAdapter
            addItemDecoration(DividerItemDecoration(16))
        }

        filmsNowPlayingAdapter = MovieRecyclerAdapter(this)
        binding.nowPlayingMovies.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false)
            adapter = filmsNowPlayingAdapter
            addItemDecoration(DividerItemDecoration(16))
        }

        tvBestAdapter = MovieRecyclerAdapter(this)
        binding.topRatedTv.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false)
            adapter = tvBestAdapter
            addItemDecoration(DividerItemDecoration(16))
        }

        tvNowOnAirAdapter = MovieRecyclerAdapter(this)
        binding.nowOnAirTv.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false)
            adapter = tvNowOnAirAdapter
            addItemDecoration(DividerItemDecoration(16))
        }

        tvTodayOnAirAdapter = MovieRecyclerAdapter(this)
        binding.onAirTodayTv.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false)
            adapter = tvTodayOnAirAdapter
            addItemDecoration(DividerItemDecoration(16))
        }

        filmsBestAdapter = MovieRecyclerAdapter(this)
        binding.topRatedMovies.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false)
            adapter = filmsBestAdapter
            addItemDecoration(DividerItemDecoration(16))
        }

        personPopularAdapter = MovieRecyclerAdapter(this)
        binding.popularPersons.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false)
            adapter = personPopularAdapter
            addItemDecoration(DividerItemDecoration(16))
        }
    }

    override fun onOpenMovie(id: Long) {
        MainActivity.openMovie(id, 1, requireActivity())
    }

    override fun onOpenTV(id: Long) {
        MainActivity.openMovie(id, 2, requireActivity())
    }

    override fun onOpenPerson(id: Long) {
        MainActivity.openMovie(id, 3, requireActivity())
    }
}