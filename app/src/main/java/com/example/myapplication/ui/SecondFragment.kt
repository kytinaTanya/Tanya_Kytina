package com.example.myapplication.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.BuildConfig
import com.example.myapplication.adapters.MovieRecyclerAdapter
import com.example.myapplication.databinding.FragmentSecondBinding
import com.example.myapplication.movies.Movie
import com.example.myapplication.movies.TmdbService
import com.example.myapplication.presenter.MoviePresenter
import com.example.myapplication.presenter.Presenter
import com.example.myapplication.repository.RepositoryImpl
import com.example.myapplication.view.MovieView


class SecondFragment : Fragment(), MovieView {
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private lateinit var mPresenter: Presenter

    lateinit var mAdapter: MovieRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = MoviePresenter.createPresenter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter = MovieRecyclerAdapter()

        binding.movieList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
        }
        mPresenter.onCreateView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.onDestroy()
        _binding = null
    }

    override fun showMovies(mList: List<Movie>) {
        mAdapter.appendMovies(mList)
    }
}