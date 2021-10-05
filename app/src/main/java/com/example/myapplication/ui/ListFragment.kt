package com.example.myapplication.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.ui.recyclerview.adapters.CollectionRecyclerAdapter
import com.example.myapplication.ui.recyclerview.adapters.ListClickListener
import com.example.myapplication.databinding.FragmentListBinding
import com.example.myapplication.models.lists.*
import com.example.myapplication.models.movies.Film
import com.example.myapplication.ui.MainActivity.Companion.USER
import com.example.myapplication.viewmodel.ListsViewModel
import dagger.hilt.android.AndroidEntryPoint

class ListFragment : Fragment(){
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListsViewModel by viewModels()
    private val mAdapter: CollectionRecyclerAdapter = CollectionRecyclerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("ListFragment", "I'm before argument")
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("ListFragment", "I'm before argument")
        arguments?.takeIf { it.containsKey("object") }?.apply {
            val list = getInt("object")
            Log.d("ListFragment", list.toString())
            when(list) {
                1 -> viewModel.loadFavoriteMoviesList(USER.sessionKey)
                2 -> viewModel.loadFavoriteTVsList(USER.sessionKey)
                3 -> viewModel.loadMovieWatchlist(USER.sessionKey)
                4 -> viewModel.loadTVWatchlist(USER.sessionKey)
            }
        }
        initRecyclerView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.favoriteMoviesList.observe(this) {
            val list: List<Film> = it.result
            Log.d("ListFragment","$list")
            mAdapter.addMovies(list)
        }
        viewModel.movieWatchlist.observe(this) {
            val list: List<Film> = it.result
            Log.d("ListFragment","$list")
            mAdapter.addMovies(list)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        binding.movieList.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

}