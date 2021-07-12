package com.example.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.MyApplicationClass
import com.example.myapplication.adapters.MovieRecyclerAdapter
import com.example.myapplication.databinding.FragmentSecondBinding
import com.example.myapplication.movies.Movie
import com.example.myapplication.viewmodel.MovieViewModel
import javax.inject.Inject


class SecondFragment : Fragment() {
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    lateinit var mAdapter: MovieRecyclerAdapter

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        MyApplicationClass.appComponent.inject(this)
        viewModel = ViewModelProvider(this, factory).get(MovieViewModel::class.java)
        super.onCreate(savedInstanceState)

        viewModel.getMovies().observe(this, Observer<List<Movie>>{ movies ->
            mAdapter.appendMovies(movies)
        })
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}