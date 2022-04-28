package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSearchResultBinding
import com.example.myapplication.ui.activities.MainActivity.Companion.MOVIE_TYPE
import com.example.myapplication.ui.activities.MainActivity.Companion.TV_TYPE
import com.example.myapplication.ui.recyclerview.adapters.SearchResultRecyclerAdapter
import com.example.myapplication.ui.recyclerview.listeners.MovieClickListener
import com.example.myapplication.utils.setConfigVerticalLinear
import com.example.myapplication.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultFragment : Fragment(), MovieClickListener, SearchViewModel.SearchView {

    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchListAdapter: SearchResultRecyclerAdapter
    private lateinit var searchRequest: String
    private val viewModel: SearchViewModel by viewModels()
    private var loaded: Int = 0
    private var resultIsEmpty: Int = 0

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
        arguments?.let {
            searchRequest = it.getString("searchRequest").toString()
            Log.d("TAG", searchRequest)
            viewModel.init(this)
            viewModel.search(searchRequest)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        showProgress(true)
        initDataObservers()
        initUI()
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
        val action = SearchResultFragmentDirections.actionSearchResultFragmentToItemInfoFragment(id,
            MOVIE_TYPE,
            0,
            0)
        view?.findNavController()?.navigate(action)
    }

    override fun onOpenTV(id: Long) {
        val action = SearchResultFragmentDirections.actionSearchResultFragmentToItemInfoFragment(id,
            TV_TYPE,
            0,
            0)
        view?.findNavController()?.navigate(action)
    }

    private fun showErrorMessage(message: String) {
        showProgress(false)
        binding.apply {
            searchList.visibility = View.GONE
            error.visibility = View.VISIBLE
            messageText.text = message
        }
    }

    private fun showProgress(show: Boolean) {
        if (show) {
            binding.apply {
                searchList.visibility = View.GONE
                error.visibility = View.GONE
                loading.visibility = View.VISIBLE
            }
        } else {
            binding.apply {
                searchList.visibility = View.VISIBLE
                loading.visibility = View.GONE
            }
        }
    }

    private fun initDataObservers() {
        viewModel.searchMovies.observe(viewLifecycleOwner) { result ->
            showProgress(!isAllLoaded())
            if (result.isEmpty()) showErrorIfResultIsEmpty()
            searchListAdapter.addMovies(result, SearchResultRecyclerAdapter.HeaderViewHolder.MOVIE)
        }
        viewModel.searchTvs.observe(viewLifecycleOwner) { result ->
            showProgress(!isAllLoaded())
            if (result.isEmpty()) showErrorIfResultIsEmpty()
            searchListAdapter.addMovies(result, SearchResultRecyclerAdapter.HeaderViewHolder.TV)
        }
    }

    private fun isAllLoaded(): Boolean {
        loaded++
        return loaded >= 2
    }

    private fun showErrorIfResultIsEmpty() {
        resultIsEmpty++
        if (resultIsEmpty == 2) {
            showErrorMessage(getString(R.string.result_error))
            binding.retryBtn.visibility = View.GONE
        }
    }

    private fun retry() {
        loaded = 0
        viewModel.search(searchRequest)
        showProgress(true)
    }

    private fun initUI() {
        binding.apply {
            retryBtn.setOnClickListener {
                retry()
            }
            toolbar.title = searchRequest
            toolbar.setNavigationOnClickListener {
                view?.findNavController()?.popBackStack()
            }
        }
    }
}