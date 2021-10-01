package com.example.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapters.MovieClickListener
import com.example.myapplication.adapters.MovieRecyclerAdapter
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.recyclerview.DividerItemDecoration
import com.example.myapplication.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(){
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

    private val viewModel: MovieViewModel by viewModels()

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerViews() {
        filmsInTrendAdapter = MovieRecyclerAdapter(object : MovieClickListener {
            override fun onOpenMovie(id: Long) {
                viewModel.getMovieDetails(id)
                openChild()
            }
        })
        binding.popularMovies.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false)
            adapter = filmsInTrendAdapter
            addItemDecoration(DividerItemDecoration(16))
        }

        filmsUpcomingAdapter = MovieRecyclerAdapter(object : MovieClickListener {
            override fun onOpenMovie(id: Long) {
                viewModel.getMovieDetails(id)
                openChild()
            }
        })
        binding.upcomingMovies.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false)
            adapter = filmsUpcomingAdapter
            addItemDecoration(DividerItemDecoration(16))
        }

        tvPopularAdapter =MovieRecyclerAdapter(object : MovieClickListener {
            override fun onOpenMovie(id: Long) {
                viewModel.getMovieDetails(id)
                openChild()
            }
        })
        binding.popularTv.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false)
            adapter = tvPopularAdapter
            addItemDecoration(DividerItemDecoration(16))
        }

        filmsNowPlayingAdapter = MovieRecyclerAdapter(object : MovieClickListener {
            override fun onOpenMovie(id: Long) {
                viewModel.getMovieDetails(id)
                openChild()
            }
        })
        binding.nowPlayingMovies.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false)
            adapter = filmsNowPlayingAdapter
            addItemDecoration(DividerItemDecoration(16))
        }

        tvBestAdapter = MovieRecyclerAdapter(object : MovieClickListener {
            override fun onOpenMovie(id: Long) {
                viewModel.getMovieDetails(id)
                openChild()
            }
        })
        binding.topRatedTv.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false)
            adapter = tvBestAdapter
            addItemDecoration(DividerItemDecoration(16))
        }

        tvNowOnAirAdapter = MovieRecyclerAdapter(object : MovieClickListener {
            override fun onOpenMovie(id: Long) {
                viewModel.getMovieDetails(id)
                openChild()
            }
        })
        binding.nowOnAirTv.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false)
            adapter = tvNowOnAirAdapter
            addItemDecoration(DividerItemDecoration(16))
        }

        tvTodayOnAirAdapter = MovieRecyclerAdapter(object : MovieClickListener {
            override fun onOpenMovie(id: Long) {
                viewModel.getMovieDetails(id)
                openChild()
            }
        })
        binding.onAirTodayTv.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false)
            adapter = tvTodayOnAirAdapter
            addItemDecoration(DividerItemDecoration(16))
        }

        filmsBestAdapter = MovieRecyclerAdapter(object : MovieClickListener {
            override fun onOpenMovie(id: Long) {
                viewModel.getMovieDetails(id)
                openChild()
            }
        })
        binding.topRatedMovies.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false)
            adapter = filmsBestAdapter
            addItemDecoration(DividerItemDecoration(16))
        }
    }

    fun openChild() {
        parentFragmentManager.commit {
            addToBackStack("MovieFragment")
            setReorderingAllowed(true)
            replace<MovieFragment>(R.id.fragment_container_view)
        }
    }
}