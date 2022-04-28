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
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSearchBinding
import com.example.myapplication.firebase.USER
import com.example.myapplication.ui.activities.MainActivity
import com.example.myapplication.ui.recyclerview.adapters.HistoryRecyclerAdapter
import com.example.myapplication.ui.recyclerview.listeners.MovieAndEpisodeListener
import com.example.myapplication.utils.setConfigHorizontalLinearWithDiv
import com.example.myapplication.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), MovieAndEpisodeListener, SearchViewModel.SearchView {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var ratedMoviesAdapter: HistoryRecyclerAdapter
    private lateinit var ratedTvsAdapter: HistoryRecyclerAdapter
    private lateinit var ratedEpisodesAdapter: HistoryRecyclerAdapter
    private lateinit var sessionKey: String
    private var loaded: Int = 0
    private val viewModel: SearchViewModel by viewModels()

    override var showError: (error: SearchViewModel.ErrorType) -> Unit = { error ->
        when (error) {
            SearchViewModel.ErrorType.NO_RESULT ->
                showErrorMessage(getString(R.string.result_error))
            SearchViewModel.ErrorType.SERVER_ERROR ->
                showErrorMessage(getString(R.string.server_error))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionKey = USER.sessionKey
        Log.d("USER LIST ID", "SESSION ID $sessionKey")
        viewModel.init(this)
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
        showProgress(true)
        initRecyclerView()
        initDataObservers()
        initUI()
    }

    private fun initDataObservers() {
        viewModel.ratedFilms.observe(viewLifecycleOwner) { films ->
            ratedMoviesAdapter.appendFilms(films)
            showProgress(!isAllLoaded())
        }
        viewModel.ratedTVs.observe(viewLifecycleOwner) { tvs ->
            ratedTvsAdapter.appendFilms(tvs)
            showProgress(!isAllLoaded())
        }
        viewModel.ratedEpisodes.observe(viewLifecycleOwner) { eps ->
            ratedEpisodesAdapter.appendFilms(eps)
            showProgress(!isAllLoaded())
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
            retryBtn.setOnClickListener {
                retry()
            }
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

    private fun showProgress(show: Boolean) {
        if (show) {
            binding.apply {
                loaded.visibility = View.GONE
                error.visibility = View.GONE
                loading.visibility = View.VISIBLE
            }
        } else {
            binding.apply {
                loaded.visibility = View.VISIBLE
                loading.visibility = View.GONE
            }
        }
    }

    private fun showErrorMessage(message: String) {
        showProgress(false)
        binding.apply {
            loaded.visibility = View.GONE
            error.visibility = View.VISIBLE
            messageText.text = message
        }
    }

    private fun isAllLoaded() : Boolean {
        loaded++
        return loaded >= 3
    }

    private fun retry() {
        loaded = 0
        viewModel.loadRatedItems(USER.sessionKey)
        showProgress(true)
    }
}