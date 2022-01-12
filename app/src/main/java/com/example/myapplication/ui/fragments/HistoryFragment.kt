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
import com.example.myapplication.databinding.FragmentHistoryBinding
import com.example.myapplication.firebase.USER
import com.example.myapplication.ui.activities.MainActivity
import com.example.myapplication.ui.recyclerview.adapters.HistoryRecyclerAdapter
import com.example.myapplication.ui.recyclerview.listeners.MovieAndEpisodeListener
import com.example.myapplication.utils.setConfigHorizontalLinearWithDiv
import com.example.myapplication.viewmodel.HistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment(), MovieAndEpisodeListener {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var ratedMoviesAdapter: HistoryRecyclerAdapter
    private lateinit var ratedTvsAdapter: HistoryRecyclerAdapter
    private lateinit var ratedEpisodesAdapter: HistoryRecyclerAdapter
    private var loaded: Int = 0
    private val viewModel: HistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("USER LIST ID", "${USER.historyListID} AND SESSION ID ${USER.sessionKey}")
        viewModel.loadRatedFilms(USER.sessionKey)
        viewModel.loadRatedTVs(USER.sessionKey)
        viewModel.loadRatedEpisodes(USER.sessionKey)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        showProgress(true)
        initRecyclerView()
        initUI()
    }

    private fun initUI() {
        binding.search.addTextChangedListener {
            if (binding.search.text.isNotEmpty()) {
                binding.searchBtn.visibility = View.VISIBLE
            } else {
                binding.searchBtn.visibility = View.GONE
            }
        }
        binding.searchBtn.setOnClickListener {
            val result = binding.search.text.toString().trim().replace(" ", "+")
            val action = HistoryFragmentDirections.actionSearchPageToSearchResultFragment(result)
            view?.findNavController()?.navigate(action)
        }
    }

    override fun onResume() {
        super.onResume()
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
        val action = HistoryFragmentDirections.actionSearchPageToItemInfoFragment(id,
            MainActivity.MOVIE_TYPE, 0, 0)
        view?.findNavController()?.navigate(action)
    }

    override fun onOpenTV(id: Long) {
        val action = HistoryFragmentDirections.actionSearchPageToItemInfoFragment(id,
            MainActivity.TV_TYPE, 0, 0)
        view?.findNavController()?.navigate(action)
    }

    override fun onOpenEpisode(tvId: Long, season: Int, episode: Int) {
        val action = HistoryFragmentDirections.actionSearchPageToItemInfoFragment(tvId,
            MainActivity.EPISODE_TYPE, season, episode)
        view?.findNavController()?.navigate(action)
    }

    private fun showProgress(show: Boolean) {
        if (show) {
            binding.apply {
                loaded.visibility = View.GONE
                loading.visibility = View.VISIBLE
            }
        } else {
            binding.apply {
                loaded.visibility = View.VISIBLE
                loading.visibility = View.GONE
            }
        }
    }

    private fun isAllLoaded() : Boolean {
        loaded++
        return loaded >= 3
    }
}