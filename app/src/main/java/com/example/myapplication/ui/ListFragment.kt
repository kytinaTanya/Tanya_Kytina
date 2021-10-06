package com.example.myapplication.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentListBinding
import com.example.myapplication.models.movies.Film
import com.example.myapplication.models.movies.TV
import com.example.myapplication.ui.MainActivity.Companion.USER
import com.example.myapplication.ui.recyclerview.adapters.CollectionRecyclerAdapter
import com.example.myapplication.ui.recyclerview.adapters.MovieClickListener
import com.example.myapplication.viewmodel.ListsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment(), MovieClickListener {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListsViewModel by viewModels()
    private lateinit var mAdapter: CollectionRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey("object") }?.apply {
            val list = getInt("object")
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        mAdapter = CollectionRecyclerAdapter(this)
        binding.movieList.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onOpenMovie(id: Long) {
        viewModel.getMovieDetails(id)
        openChild()
    }

    override fun onOpenTV(id: Long) {
        Log.d("TAG", "TAG")
        viewModel.getTVDetails(id)
        openChild()
    }

    fun openChild() {
        parentFragment?.parentFragmentManager?.commit {
            addToBackStack("ListFragment")
            setReorderingAllowed(true)
            replace<MovieFragment>(R.id.fragment_container_view)
        }
    }

    override fun onOpenPerson(id: Long) {
        TODO("Not yet implemented")
    }

}