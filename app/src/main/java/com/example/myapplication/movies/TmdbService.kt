package com.example.myapplication.movies

import com.example.myapplication.BuildConfig
import com.example.myapplication.room.entity.Movie
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbService {

    companion object {
        fun createApiService(): TmdbService {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TmdbService::class.java)
        }
    }

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String,
        @Query("page") page: Int = 1
    ): Response<MoviesResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") id: Long,
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String
    ): Response<Movie>
}