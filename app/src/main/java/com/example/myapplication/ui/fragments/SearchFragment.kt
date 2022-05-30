package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.myapplication.databinding.FragmentSearchBinding
import com.example.myapplication.firebase.USER
import com.example.myapplication.states.SearchScreenViewState
import com.example.myapplication.ui.activities.MainActivity
import com.example.myapplication.ui.recyclerview.adapters.HistoryRecyclerAdapter
import com.example.myapplication.ui.recyclerview.listeners.MovieAndEpisodeListener
import com.example.myapplication.utils.setConfigHorizontalLinearWithDiv
import com.example.myapplication.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), MovieAndEpisodeListener {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var ratedMoviesAdapter: HistoryRecyclerAdapter
    private lateinit var ratedTvsAdapter: HistoryRecyclerAdapter
    private lateinit var ratedEpisodesAdapter: HistoryRecyclerAdapter
    private lateinit var sessionKey: String
    private var loaded: Int = 0
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionKey = USER.sessionKey
        Log.d("USER LIST ID", "SESSION ID $sessionKey")
        viewModel.loadRatedItems(USER.sessionKey)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        initDataObservers()
        initUI()
    }

    private fun initDataObservers() {
        viewModel.ratedFilms.observe(viewLifecycleOwner) { result ->
            when (result) {
                SearchScreenViewState.Error -> showRatedFilms(View.GONE, View.GONE, View.VISIBLE)
                SearchScreenViewState.Loading -> showRatedFilms(View.GONE, View.VISIBLE, View.GONE)
                is SearchScreenViewState.Success.FilmSuccess -> {
                    showRatedFilms(View.VISIBLE, View.GONE, View.GONE)
                    ratedMoviesAdapter.appendFilms(result.films)
                }
            }
        }
        viewModel.ratedTVs.observe(viewLifecycleOwner) { result ->
            when (result) {
                SearchScreenViewState.Error -> showRatedTvs(View.GONE, View.GONE, View.VISIBLE)
                SearchScreenViewState.Loading -> showRatedTvs(View.GONE, View.VISIBLE, View.GONE)
                is SearchScreenViewState.Success.TvSuccess -> {
                    showRatedTvs(View.VISIBLE, View.GONE, View.GONE)
                    ratedTvsAdapter.appendFilms(result.tvs)
                }
            }
        }
        viewModel.ratedEpisodes.observe(viewLifecycleOwner) { result ->
            when (result) {
                SearchScreenViewState.Error -> showRatedEpisodes(View.GONE, View.GONE, View.VISIBLE)
                SearchScreenViewState.Loading -> showRatedEpisodes(View.GONE, View.VISIBLE, View.GONE)
                is SearchScreenViewState.Success.EpisodeSuccess -> {
                    showRatedEpisodes(View.VISIBLE, View.GONE, View.GONE)
                    ratedEpisodesAdapter.appendFilms(result.episodes)
                }
            }
        }
    }

    private fun initUI() {
        binding.apply {
            search.addTextChangedListener {
                if (binding.search.text.isNotEmpty()) {
                    binding.searchBtn.visibility = View.VISIBLE
                } else {
                    binding.searchBtn.visibility = View.GONE
                }
            }
            searchBtn.setOnClickListener {
                val result = binding.search.text.toString().trim().replace(" ", "+")
                val action = SearchFragmentDirections.actionSearchPageToSearchResultFragment(result)
                view?.findNavController()?.navigate(action)
            }
            ratedMoviesError.errorButton.setOnClickListener { viewModel.loadRatedFilms(USER.sessionKey) }
            ratedTvsError.errorButton.setOnClickListener { viewModel.loadRatedTvs(USER.sessionKey) }
            ratedEpisodesError.errorButton.setOnClickListener { viewModel.loadRatedEpisodes(USER.sessionKey) }
        }
    }

    private fun initRecyclerView() {
        ratedMoviesAdapter = HistoryRecyclerAdapter(this)
        binding.ratedMoviesList.setConfigHorizontalLinearWithDiv(ratedMoviesAdapter, requireContext(), 32)

        ratedTvsAdapter = HistoryRecyclerAdapter(this)
        binding.ratedTvsList.setConfigHorizontalLinearWithDiv(ratedTvsAdapter, requireContext(), 32)

        ratedEpisodesAdapter = HistoryRecyclerAdapter(this)
        binding.ratedEpisodesList.setConfigHorizontalLinearWithDiv(ratedEpisodesAdapter, requireContext(), 32)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onOpenMovie(id: Long) {
        val action = SearchFragmentDirections.actionSearchPageToItemInfoFragment(id,
            MainActivity.MOVIE_TYPE, 0, 0)
        view?.findNavController()?.navigate(action)
    }

    override fun onOpenTV(id: Long) {
        val action = SearchFragmentDirections.actionSearchPageToItemInfoFragment(id,
            MainActivity.TV_TYPE, 0, 0)
        view?.findNavController()?.navigate(action)
    }

    override fun onOpenEpisode(tvId: Long, season: Int, episode: Int) {
        val action = SearchFragmentDirections.actionSearchPageToItemInfoFragment(tvId,
            MainActivity.EPISODE_TYPE, season, episode)
        view?.findNavController()?.navigate(action)
    }

    private fun showRatedFilms(result: Int, loading: Int, error: Int) {
        binding.apply {
            ratedMoviesList.visibility = result
            ratedMoviesProgress.visibility = loading
            ratedMoviesError.errorView.visibility = error
        }
    }

    private fun showRatedTvs(result: Int, loading: Int, error: Int) {
        binding.apply {
            ratedTvsList.visibility = result
            ratedTvsProgress.visibility = loading
            ratedTvsError.errorView.visibility = error
        }
    }

    private fun showRatedEpisodes(result: Int, loading: Int, error: Int) {
        binding.apply {
            ratedEpisodesList.visibility = result
            ratedEpisodesProgress.visibility = loading
            ratedEpisodesError.errorView.visibility = error
        }
    }
}