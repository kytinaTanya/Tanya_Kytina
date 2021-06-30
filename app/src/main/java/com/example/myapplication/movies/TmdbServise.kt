package com.example.myapplication.movies

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbServise {

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = "e19d111501403fed5ffe087a81ca958f",
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<MoviesResponse>
}