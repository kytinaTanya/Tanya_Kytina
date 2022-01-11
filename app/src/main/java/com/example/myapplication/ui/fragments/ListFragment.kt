package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.myapplication.databinding.FragmentListBinding
import com.example.myapplication.firebase.USER
import com.example.myapplication.models.pojo.Film
import com.example.myapplication.models.pojo.TV
import com.example.myapplication.ui.activities.MainActivity
import com.example.myapplication.ui.recyclerview.adapters.CollectionRecyclerAdapter
import com.example.myapplication.ui.recyclerview.listeners.MovieClickListener
import com.example.myapplication.utils.setConfigVerticalLinear
import com.example.myapplication.viewmodel.ListsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment(), MovieClickListener {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListsViewModel by viewModels()
    private lateinit var mAdapter: CollectionRecyclerAdapter
    private var list = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey("object") }?.apply {
            list = getInt("object")
        }

    }

    private fun loadData() {
        when(list) {
            1 -> viewModel.loadFavoriteMoviesList(USER.sessionKey)
            2 -> viewModel.loadFavoriteTVsList(USER.sessionKey)
            3 -> viewModel.loadMovieWatchlist(USER.sessionKey)
            4 -> viewModel.loadTVWatchlist(USER.sessionKey)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.favoriteMoviesList.observe(this) {
            if(it.totalResults == 0) {
                binding.movieList.visibility = View.GONE
                binding.warning.visibility = View.VISIBLE
            }
            val list: List<Film> = it.result
            Log.d("ListFragment","$list")
            binding.movieList.visibility = View.VISIBLE
            binding.warning.visibility = View.GONE
            mAdapter.addMovies(list)
        }
        viewModel.favoriteTVsList.observe(this) {
            if(it.totalResults == 0) {
                binding.movieList.visibility = View.GONE
                binding.warning.visibility = View.VISIBLE
            }
            val list: List<TV> = it.result
            Log.d("ListFragment","$list")
            binding.movieList.visibility = View.VISIBLE
            binding.warning.visibility = View.GONE
            mAdapter.addMovies(list)
        }
        viewModel.movieWatchlist.observe(this) {
            if(it.totalResults == 0) {
                binding.movieList.visibility = View.GONE
                binding.warning.visibility = View.VISIBLE
            }
            val list: List<Film> = it.result
            Log.d("ListFragment","$list")
            binding.movieList.visibility = View.VISIBLE
            binding.warning.visibility = View.GONE
            mAdapter.addMovies(list)
        }
        viewModel.tvWatchlist.observe(this) {
            if(it.totalResults == 0) {
                binding.movieList.visibility = View.GONE
                binding.warning.visibility = View.VISIBLE
            }
            val list: List<TV> = it.result
            Log.d("ListFragment","$list")
            binding.movieList.visibility = View.VISIBLE
            binding.warning.visibility = View.GONE
            mAdapter.addMovies(list)
        }
    }

    override fun onStart() {
        super.onStart()
        loadData()
        initRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        mAdapter = CollectionRecyclerAdapter(this)
        binding.movieList.setConfigVerticalLinear(mAdapter = mAdapter, context = requireContext())
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
}