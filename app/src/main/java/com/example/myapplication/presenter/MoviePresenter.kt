package com.example.myapplication.presenter

import android.util.Log
import com.example.myapplication.BuildConfig
import com.example.myapplication.movies.Movie
import com.example.myapplication.movies.TmdbService
import com.example.myapplication.repository.Repository
import com.example.myapplication.repository.RepositoryImpl
import com.example.myapplication.view.MovieView

class MoviePresenter(
    private val mView: MovieView,
    private val mRepository: Repository
    ) : Presenter {

    private val tag: String = MoviePresenter::class.java.simpleName

    override fun onCreateView() {
        mRepository.getData(::onTopRatedMoviesFetched, ::onError)
    }

    private fun onTopRatedMoviesFetched(list: List<Movie>) {
        mView.showMovies(list)
    }

    private fun onError() {
        Log.d(tag, "Something don't work")
    }

    override fun onDestroy() {
        Log.d(tag, "onDestroy()")
    }

    companion object {
        fun createPresenter(view: MovieView) = MoviePresenter(view, RepositoryImpl(TmdbService.createApiService()))
    }
}