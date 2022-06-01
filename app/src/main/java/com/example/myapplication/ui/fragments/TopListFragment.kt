package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentTopListBinding
import com.example.myapplication.ui.recyclerview.VerticalItemsDividerDecoration
import com.example.myapplication.ui.recyclerview.adapters.TopListPagingAdapter
import com.example.myapplication.ui.recyclerview.adapters.loadstate.ItemListLoadStateAdapter
import com.example.myapplication.ui.recyclerview.listeners.MovieAndTvAndPersonListener
import com.example.myapplication.viewmodel.MainScreenRequest
import com.example.myapplication.viewmodel.TopListsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopListFragment : Fragment(), MovieAndTvAndPersonListener {
    private var _binding: FragmentTopListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TopListsViewModel by viewModels()

    lateinit var mAdapter: TopListPagingAdapter
    private var requestType: MainScreenRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestType = arguments?.get("request") as MainScreenRequest
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTopListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter = TopListPagingAdapter(this)
        binding.errorButton.setOnClickListener { mAdapter.retry() }
        binding.movieList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter.withLoadStateHeaderAndFooter(
                ItemListLoadStateAdapter(mAdapter::retry),
                ItemListLoadStateAdapter(mAdapter::retry)
            )
            addItemDecoration(VerticalItemsDividerDecoration(innerDivider = 32, outerDivider = 4))
        }

        binding.toolbar.title = getToolbarTitle(requestType)
        binding.toolbar.setNavigationOnClickListener {
            view.findNavController().popBackStack()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getData(requestType ?: MainScreenRequest.TOP_RATED_MOVIES).collectLatest {
                mAdapter.submitData(it)
            }

        }

        viewLifecycleOwner.lifecycleScope.launch {
            mAdapter.loadStateFlow.collectLatest { state ->
                binding.progress.isVisible = state.refresh is LoadState.Loading
                binding.movieList.isVisible = state.refresh !is LoadState.Loading
                binding.errorText.isVisible = state.refresh is LoadState.Error
                binding.errorButton.isVisible = state.refresh is LoadState.Error
            }
        }
    }

    override fun onOpenMovie(id: Long) {
        val action = TopListFragmentDirections.actionTopListFragmentToFilmInfoFragment(id)
        view?.findNavController()?.navigate(action)
    }

    override fun onOpenTV(id: Long) {
        val action = TopListFragmentDirections.actionTopListFragmentToTvInfoFragment(id)
        view?.findNavController()?.navigate(action)
    }

    override fun onOpenPerson(id: Long) {
        val action = TopListFragmentDirections.actionTopListFragmentToPersonInfoFragment(id)
        view?.findNavController()?.navigate(action)
    }

    private fun getToolbarTitle(requestType: MainScreenRequest?): CharSequence {
        return when (requestType) {
            MainScreenRequest.TOP_RATED_MOVIES ->
                getFormattedString(
                    getString(R.string.the_best_title),
                    getString(R.string.movies_subtitle)
                )
            MainScreenRequest.POPULAR_MOVIES ->
                getFormattedString(
                    getString(R.string.in_trend_title),
                    getString(R.string.movies_subtitle)
                )
            MainScreenRequest.NOW_PLAYING_MOVIES ->
                getFormattedString(
                    getString(R.string.now_watching_title),
                    getString(R.string.movies_subtitle)
                )
            MainScreenRequest.UPCOMING_MOVIES ->
                getFormattedString(
                    getString(R.string.soon_title),
                    getString(R.string.movies_subtitle)
                )
            MainScreenRequest.TOP_RATED_TVS ->
                getFormattedString(
                    getString(R.string.the_best_title),
                    getString(R.string.tvs_subtitle)
                )
            MainScreenRequest.AIRING_TODAY_TVS ->
                getFormattedString(
                    getString(R.string.today_on_air),
                    getString(R.string.tvs_subtitle)
                )
            MainScreenRequest.ON_THE_AIR_TVS ->
                getFormattedString(
                    getString(R.string.now_on_air_title),
                    getString(R.string.tvs_subtitle)
                )
            MainScreenRequest.POPULAR_TVS ->
                getFormattedString(
                    getString(R.string.in_trend_title),
                    getString(R.string.tvs_subtitle)
                )
            MainScreenRequest.POPULAR_PERSONS -> getString(R.string.popular_actors_title)
            else -> getString(R.string.top_list_toolbar_title)
        }
    }

    private fun getFormattedString(firstString: String, secondString: String): String =
        "$firstString ($secondString)"
}