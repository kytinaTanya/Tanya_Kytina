package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.*
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentHistoryBinding
import com.example.myapplication.firebase.USER
import com.example.myapplication.ui.activities.MainActivity
import com.example.myapplication.ui.recyclerview.DividerItemDecoration
import com.example.myapplication.ui.recyclerview.adapters.HistoryRecyclerAdapter
import com.example.myapplication.ui.recyclerview.listeners.EpisodeClickListener
import com.example.myapplication.ui.recyclerview.listeners.MovieAndEpisodeListener
import com.example.myapplication.ui.recyclerview.listeners.MovieClickListener
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
        viewModel.ratedFilms.observe(this) { films ->
            ratedMoviesAdapter.appendFilms(films)
        }
        viewModel.ratedTVs.observe(this) { tvs ->
            ratedTvsAdapter.appendFilms(tvs)
        }
        viewModel.ratedEpisodes.observe(this) { eps ->
            ratedEpisodesAdapter.appendFilms(eps)
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
}