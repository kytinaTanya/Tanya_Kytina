package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentListBinding
import com.example.myapplication.firebase.USER
import com.example.myapplication.ui.recyclerview.VerticalItemsDividerDecoration
import com.example.myapplication.ui.recyclerview.adapters.CollectionRecyclerAdapter
import com.example.myapplication.ui.recyclerview.adapters.loadstate.ItemListLoadStateAdapter
import com.example.myapplication.ui.recyclerview.listeners.MovieAndTvClickListener
import com.example.myapplication.utils.hideAnimated
import com.example.myapplication.utils.showAnimated
import com.example.myapplication.viewmodel.UserListsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserListFragment : Fragment(), MovieAndTvClickListener {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserListsViewModel by viewModels()
    private lateinit var mAdapter: CollectionRecyclerAdapter
    private var list = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey("object") }?.apply {
            list = getInt("object")
        }
        initRecyclerView()
        loadData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onOpenMovie(id: Long) {
        val action = FavoriteFragmentDirections.actionFavoritePageToFilmInfoFragment(id)
        view?.findNavController()?.navigate(action)
    }

    override fun onOpenTV(id: Long) {
        val action = FavoriteFragmentDirections.actionFavoritePageToTvInfoFragment(id)
        view?.findNavController()?.navigate(action)
    }

    private fun loadData() {
        when (list) {
            1 -> {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.loadFavoriteMoviesList(USER.sessionKey).collectLatest { item ->
                        mAdapter.submitData(item)
                    }
                }
            }
            2 -> {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.getFavoriteTVsList(USER.sessionKey).collectLatest { item ->
                        mAdapter.submitData(item)
                    }
                }
            }
            3 -> {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.loadMovieWatchlist(USER.sessionKey).collectLatest { item ->
                        mAdapter.submitData(item)
                    }
                }
            }
            4 -> {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.loadTVWatchlist(USER.sessionKey).collectLatest { item ->
                        mAdapter.submitData(item)
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        mAdapter = CollectionRecyclerAdapter(this)
        binding.movieList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter.withLoadStateHeaderAndFooter(
                ItemListLoadStateAdapter(mAdapter::retry),
                ItemListLoadStateAdapter(mAdapter::retry)
            )
            addItemDecoration(VerticalItemsDividerDecoration(innerDivider = 32, outerDivider = 32))
        }
        binding.errorButton.setOnClickListener { mAdapter.refresh() }
        viewLifecycleOwner.lifecycleScope.launch {
            mAdapter.loadStateFlow.collectLatest { state ->
                handleLoadingState(state.refresh)
                handleErrorState(state.refresh)
            }
        }
    }

    private fun handleLoadingState(state: LoadState) {
        if (state is LoadState.Loading) {
            binding.apply {
                progress.showAnimated()
                movieList.visibility = View.GONE
                warning.visibility = View.GONE
            }
        } else {
            binding.progress.hideAnimated()
            if (mAdapter.itemCount == 0) binding.warning.showAnimated() else binding.movieList.showAnimated()
        }
    }

    private fun handleErrorState(state: LoadState) {
        if (state is LoadState.Error) {
            binding.apply {
                warning.visibility = View.GONE
                errorText.showAnimated()
                errorButton.showAnimated()
            }
        } else {
            binding.apply {
                errorText.hideAnimated()
                errorButton.hideAnimated()
            }
        }
    }
}