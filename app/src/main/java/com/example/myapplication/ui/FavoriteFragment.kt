package com.example.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myapplication.adapters.ListsRecyclerAdapter
import com.example.myapplication.databinding.FragmentFavoriteBinding
import com.example.myapplication.ui.MainActivity.Companion.USER
import com.example.myapplication.viewmodel.ListsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var listAdapter: ListsRecyclerAdapter

    private val viewModel: ListsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadCreatedLists(USER.sessionKey)
        viewModel.loadFavoriteMoviesList(USER.sessionKey)
        viewModel.loadFavoriteTVsList(USER.sessionKey)
        viewModel.loadMovieWatchlist(USER.sessionKey)
        viewModel.loadTVWatchlist(USER.sessionKey)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        listAdapter = ListsRecyclerAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}