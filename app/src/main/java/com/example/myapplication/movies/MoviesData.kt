package com.example.myapplication.movies

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MoviesData {
    private val servise: TmdbServise

    init{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        servise = retrofit.create(TmdbServise::class.java)
    }

    fun getTopRatedMovies(
        page: Int = 1,
        language: String = "en-US",
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit
    ) {
        servise.getTopRatedMovies(page = page, language = language)
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