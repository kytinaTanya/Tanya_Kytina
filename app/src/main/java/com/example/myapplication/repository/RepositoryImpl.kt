package com.example.myapplication.repository

import com.example.myapplication.movies.Movie
import com.example.myapplication.movies.MoviesResponse
import com.example.myapplication.movies.TmdbServise
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryImpl(val servise: TmdbServise) : Repository {

    override fun getData(onSuccess: (List<Movie>) -> Unit, onError: () -> Unit) {
        // получение данных позже отрефакторить нужно под коррутины и RX
        servise.getTopRatedMovies(language = "ru-RU")
            .enqueue(object : Callback<MoviesResponse> {
                override fun onResponse(
                    call: Call<MoviesResponse>,
                    response: Response<MoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess(responseBody.movies)
                        } else {
                            onError()
                        }
                    } else {
                        onError()
                    }
                }

                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    onError()
                }
            })
    }
}