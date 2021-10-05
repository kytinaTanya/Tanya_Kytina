package com.example.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.*
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.ui.recyclerview.adapters.ListClickListener
import com.example.myapplication.ui.recyclerview.adapters.ListsRecyclerAdapter
import com.example.myapplication.databinding.FragmentFavoriteBinding
import com.example.myapplication.models.lists.*
import com.example.myapplication.ui.recyclerview.DividerItemDecoration
import com.example.myapplication.ui.MainActivity.Companion.USER
import com.example.myapplication.ui.viewpager.MoviesCollectionAdapter
import com.example.myapplication.viewmodel.ListsViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieCollectionAdapter: MoviesCollectionAdapter

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
        movieCollectionAdapter = MoviesCollectionAdapter(this)
        binding.pager.adapter = movieCollectionAdapter
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = when(position) {
                0 -> "Любимые фильмы"
                1 -> "Любимые сериалы"
                2 -> "Watchlist фильмов"
                3 -> "Watchlist сериалов"
                else -> ""
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}