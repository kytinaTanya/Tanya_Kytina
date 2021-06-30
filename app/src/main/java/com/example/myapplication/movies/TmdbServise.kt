package com.example.myapplication.movies

import com.example.myapplication.BuildConfig
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbServise {

    companion object {
        fun createApiService(): TmdbServise {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TmdbServise::class.java)
        }
    }

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String,
        @Query("page") page: Int = 1
    ): Call<MoviesResponse>
}