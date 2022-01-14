package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.myapplication.databinding.FragmentSearchResultBinding
import com.example.myapplication.ui.activities.MainActivity.Companion.MOVIE_TYPE
import com.example.myapplication.ui.activities.MainActivity.Companion.TV_TYPE
import com.example.myapplication.ui.recyclerview.adapters.SearchResultRecyclerAdapter
import com.example.myapplication.ui.recyclerview.listeners.MovieClickListener
import com.example.myapplication.utils.setConfigVerticalLinear
import com.example.myapplication.viewmodel.HistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultFragment : Fragment(), MovieClickListener {

    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchListAdapter: SearchResultRecyclerAdapter
    private val viewModel: HistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val result = it.getString("searchRequest").toString()
            Log.d("TAG", result)
            viewModel.searchMovie(result)
            viewModel.searchTV(result)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        viewModel.searchMovies.observe(viewLifecycleOwner) { result ->
            searchListAdapter.addMovies(result, SearchResultRecyclerAdapter.HeaderViewHolder.MOVIE)
        }
        viewModel.searchTvs.observe(viewLifecycleOwner) { result ->
            searchListAdapter.addMovies(result, SearchResultRecyclerAdapter.HeaderViewHolder.TV)
        }
    }

    private fun initRecyclerView() {
        searchListAdapter = SearchResultRecyclerAdapter(this)
        binding.searchList.setConfigVerticalLinear(searchListAdapter, requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onOpenMovie(id: Long) {
        val action = SearchResultFragmentDirections.actionSearchResultFragmentToItemInfoFragment(id, MOVIE_TYPE, 0, 0)
        view?.findNavController()?.navigate(action)
    }

    override fun onOpenTV(id: Long) {
        val action = SearchResultFragmentDirections.actionSearchResultFragmentToItemInfoFragment(id, TV_TYPE, 0, 0)
        view?.findNavController()?.navigate(action)
    }
}