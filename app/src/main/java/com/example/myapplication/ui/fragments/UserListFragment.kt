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
import com.example.myapplication.ui.activities.MainActivity
import com.example.myapplication.ui.recyclerview.VerticalItemsDividerDecoration
import com.example.myapplication.ui.recyclerview.adapters.CollectionRecyclerAdapter
import com.example.myapplication.ui.recyclerview.adapters.loadstate.ItemListLoadStateAdapter
import com.example.myapplication.ui.recyclerview.listeners.MovieClickListener
import com.example.myapplication.viewmodel.UserListsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserListFragment : Fragment(), MovieClickListener {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onOpenMovie(id: Long) {
        val action = FavoriteFragmentDirections.actionFavoritePageToItemInfoFragment(id,
            MainActivity.MOVIE_TYPE, 0, 0)
        view?.findNavController()?.navigate(action)
    }

    override fun onOpenTV(id: Long) {
        val action = FavoriteFragmentDirections.actionFavoritePageToItemInfoFragment(id,
            MainActivity.TV_TYPE, 0, 0)
        view?.findNavController()?.navigate(action)
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
                progress.visibility = View.VISIBLE
                movieList.visibility = View.GONE
                warning.visibility = View.GONE
            }
        } else {
            binding.progress.visibility = View.GONE
            if (mAdapter.itemCount == 0) binding.warning.visibility =
                View.VISIBLE else binding.movieList.visibility = View.VISIBLE
        }
    }

    private fun handleErrorState(state: LoadState) {
        if (state is LoadState.Error) {
            binding.apply {
                warning.visibility = View.GONE
                errorText.visibility = View.VISIBLE
                errorButton.visibility = View.VISIBLE
            }
        } else {
            binding.apply {
                errorText.visibility = View.GONE
                errorButton.visibility = View.GONE
            }
        }
    }
}