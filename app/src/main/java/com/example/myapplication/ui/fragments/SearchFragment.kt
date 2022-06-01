package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.myapplication.databinding.FragmentSearchBinding
import com.example.myapplication.firebase.USER
import com.example.myapplication.states.SearchScreenViewState
import com.example.myapplication.ui.recyclerview.adapters.HistoryRecyclerAdapter
import com.example.myapplication.ui.recyclerview.listeners.MovieAndTvAndEpisodeListener
import com.example.myapplication.utils.hideAnimated
import com.example.myapplication.utils.setConfigHorizontalLinearWithDiv
import com.example.myapplication.utils.showAnimated
import com.example.myapplication.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), MovieAndTvAndEpisodeListener {

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
                SearchScreenViewState.Error -> {
                    binding.ratedMoviesList.visibility = View.GONE
                    binding.ratedMoviesProgress.hideAnimated()
                    binding.ratedMoviesError.errorView.showAnimated()
                }
                SearchScreenViewState.Loading -> {
                    binding.ratedMoviesList.visibility = View.GONE
                    binding.ratedMoviesProgress.showAnimated()
                    binding.ratedMoviesError.errorView.hideAnimated()
                }
                is SearchScreenViewState.Success.FilmSuccess -> {
                    binding.ratedMoviesList.showAnimated()
                    binding.ratedMoviesProgress.hideAnimated()
                    binding.ratedMoviesError.errorView.visibility = View.GONE
                    ratedMoviesAdapter.appendFilms(result.films)
                }
            }
        }
        viewModel.ratedTVs.observe(viewLifecycleOwner) { result ->
            when (result) {
                SearchScreenViewState.Error -> {
                    binding.ratedTvsList.visibility = View.GONE
                    binding.ratedTvsProgress.hideAnimated()
                    binding.ratedTvsError.errorView.showAnimated()
                }
                SearchScreenViewState.Loading -> {
                    binding.ratedTvsList.visibility = View.GONE
                    binding.ratedTvsProgress.showAnimated()
                    binding.ratedTvsError.errorView.hideAnimated()
                }
                is SearchScreenViewState.Success.TvSuccess -> {
                    binding.ratedTvsList.showAnimated()
                    binding.ratedTvsProgress.hideAnimated()
                    binding.ratedTvsError.errorView.visibility = View.GONE
                    ratedTvsAdapter.appendFilms(result.tvs)
                }
            }
        }
        viewModel.ratedEpisodes.observe(viewLifecycleOwner) { result ->
            when (result) {
                SearchScreenViewState.Error -> {
                    binding.ratedEpisodesList.visibility = View.GONE
                    binding.ratedEpisodesProgress.hideAnimated()
                    binding.ratedEpisodesError.errorView.showAnimated()
                }
                SearchScreenViewState.Loading -> {
                    binding.ratedEpisodesList.visibility = View.GONE
                    binding.ratedEpisodesProgress.showAnimated()
                    binding.ratedEpisodesError.errorView.hideAnimated()
                }
                is SearchScreenViewState.Success.EpisodeSuccess -> {
                    binding.ratedEpisodesList.showAnimated()
                    binding.ratedEpisodesProgress.hideAnimated()
                    binding.ratedEpisodesError.errorView.visibility = View.GONE
                    ratedEpisodesAdapter.appendFilms(result.episodes)
                }
            }
        }
    }

    private fun initUI() {
        binding.apply {
            search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    val action = SearchFragmentDirections.actionSearchPageToSearchResultFragment(query ?: "")
                    view?.findNavController()?.navigate(action)
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
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
        val action = SearchFragmentDirections.actionSearchPageToFilmInfoFragment(id)
        view?.findNavController()?.navigate(action)
    }

    override fun onOpenTV(id: Long) {
        val action = SearchFragmentDirections.actionSearchPageToTvInfoFragment(id)
        view?.findNavController()?.navigate(action)
    }

    override fun onOpenEpisode(tvId: Long, season: Int, episode: Int) {
        val action =
            SearchFragmentDirections.actionSearchPageToEpisodeInfoFragment(tvId, season, episode)
        view?.findNavController()?.navigate(action)
    }
}