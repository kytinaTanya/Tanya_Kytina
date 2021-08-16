package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.myapplication.movies.TmdbService
import com.example.myapplication.repository.Repository
import com.example.myapplication.repository.inMemory.MoviePagingSource
import com.example.myapplication.repository.inMemory.RepositoryImpl
import javax.inject.Inject

class MovieViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val tag: String = MovieViewModel::class.java.simpleName

    val movies = repository.getData()

//    val movies: MutableLiveData<List<Movie>> by lazy {
//        MutableLiveData<List<Movie>>().also {
//            loadMovies()
//        }
//    }
//
//    fun getMovies(): LiveData<List<Movie>>{
//        return movies
//    }
//
//    private fun loadMovies() {
//        viewModelScope.launch {
//            mRepository.getData(::onTopRatedMoviesFetched, ::onError)
//        }
//    }
//
//    private fun onTopRatedMoviesFetched(list: List<Movie>) {
//        movies.value = list
//    }
//
//    private fun onError() {
//        Log.d(tag, "Something don't work")
//    }

}