package com.example.myapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.MyApplicationClass
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMovieBinding
import com.example.myapplication.databinding.FragmentSecondBinding
import com.example.myapplication.viewmodel.MovieViewModel
import javax.inject.Inject

class MovieFragment : Fragment() {
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        MyApplicationClass.appComponent.inject(this)
        viewModel = ViewModelProvider(this, factory).get(MovieViewModel::class.java)
        super.onCreate(savedInstanceState)


        viewModel.movieDetails.observe(this ){

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}