package com.example.myapplication.presenter

import android.util.Log
import com.example.myapplication.BuildConfig
import com.example.myapplication.movies.Movie
import com.example.myapplication.movies.TmdbService
import com.example.myapplication.repository.Repository
import com.example.myapplication.repository.RepositoryImpl
import com.example.myapplication.view.MovieView

class MoviePresenter(private var mView: MovieView) : Presenter {

    private val tag: String = "MoviePresenter"

    private var mRepository: Repository = RepositoryImpl(TmdbService.createApiService())

    override fun onCreateView() {
        mRepository.getData(::onTopRatedMoviesFetched, ::onError)
    }

    private fun onTopRatedMoviesFetched(list: List<Movie>) {
        mView.showMovies(list)
        loggingImages(list)
    }

    private fun onError() {
        Log.d(tag, "Something don't work")
    }

    private fun loggingImages(list: List<Movie>) {
        val task = Thread {
            list.forEach {
                Log.d("Image", BuildConfig.BASE_IMAGE_URL + it.posterPath)
            }
        }
        task.start()
        task.join()
    }

    override fun onDestroy() {
        Log.d(tag, "onDestroy()")
    }
}