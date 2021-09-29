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

    lateinit var inTrendAdapter: MovieRecyclerAdapter
    lateinit var recommendationAdapter: MovieRecyclerAdapter
    lateinit var ratedAdapter: MovieRecyclerAdapter
    lateinit var newAdapter: MovieRecyclerAdapter

    private val viewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadMoviesInTrend()
        viewModel.loadNewMovies()
        viewModel.loadRecommendations()
        viewModel.loadPopularTV()
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
            inTrendAdapter.appendMovies(movies)
        }

        viewModel.moviesNew.observe(this) { movies ->
            newAdapter.appendMovies(movies)
        }

        viewModel.moviesRecommend.observe(this) { movies ->
            recommendationAdapter.appendMovies(movies)
        }

        viewModel.popularTV.observe(this) { movies ->
            ratedAdapter.appendMovies(movies)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerViews() {
        inTrendAdapter = MovieRecyclerAdapter(object : MovieClickListener {
            override fun onOpenMovie(id: Long) {
                viewModel.getMovieDetails(id)
                openChild()
            }
        })
        binding.inTrendRV.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false)
            adapter = inTrendAdapter
            addItemDecoration(DividerItemDecoration(16))
        }

        recommendationAdapter = MovieRecyclerAdapter(object : MovieClickListener {
            override fun onOpenMovie(id: Long) {
                viewModel.getMovieDetails(id)
                openChild()
            }
        })
        binding.recRV.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false)
            adapter = recommendationAdapter
            addItemDecoration(DividerItemDecoration(16))
        }

        ratedAdapter =MovieRecyclerAdapter(object : MovieClickListener {
            override fun onOpenMovie(id: Long) {
                viewModel.getMovieDetails(id)
                openChild()
            }
        })
        binding.ratedRV.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false)
            adapter = ratedAdapter
            addItemDecoration(DividerItemDecoration(16))
        }

        newAdapter = MovieRecyclerAdapter(object : MovieClickListener {
            override fun onOpenMovie(id: Long) {
                viewModel.getMovieDetails(id)
                openChild()
            }
        })
        binding.newRV.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false)
            adapter = newAdapter
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