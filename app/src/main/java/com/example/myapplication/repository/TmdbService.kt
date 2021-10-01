package com.example.myapplication.repository

import com.example.myapplication.BuildConfig
import com.example.myapplication.models.*
import com.example.myapplication.models.lists.*
import com.example.myapplication.models.movies.Film
import com.example.myapplication.models.movies.MoviesResponse
import com.example.myapplication.models.movies.PersonResponse
import com.example.myapplication.models.movies.TVResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

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

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") id: Long,
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String
    ): Response<Film>

    @GET("authentication/token/new")
    suspend fun getRequestToken(
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH
    ): Response<RequestToken>

    @POST("authentication/session/new")
    suspend fun postSession(
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Body body: RetrofitPostToken
    ): Response<SessionId>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = "ru-RU",
        @Query("page") page: Int = 1,
        @Query("region") region: String = "RU"
    ): Response<MoviesResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = "ru-RU",
        @Query("page") page: Int = 1,
        @Query("region") region: String = "RU"
    ): Response<MoviesResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = "ru-RU",
        @Query("page") page: Int = 1,
        @Query("region") region: String = "RU"
    ): Response<MoviesResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = "ru-RU",
        @Query("page") page: Int = 1
    ): Response<MoviesResponse>

    @GET("tv/popular")
    suspend fun getPopularTV(
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = "ru-RU",
        @Query("page") page: Int = 1
    ): Response<TVResponse>

    @GET("tv/airing_today")
    suspend fun getOnAirTodayTV(
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = "ru-RU",
        @Query("page") page: Int = 1
    ): Response<TVResponse>

    @GET("tv/on_the_air")
    suspend fun getNowOnAirTV(
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = "ru-RU",
        @Query("page") page: Int = 1
    ): Response<TVResponse>

    @GET("tv/top_rated")
    suspend fun getTopRatedTV(
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = "ru-RU",
        @Query("page") page: Int = 1
    ): Response<TVResponse>

    @GET("person/popular")
    suspend fun getPopularPersons(
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = "ru-RU",
        @Query("page") page: Int = 1
    ): Response<PersonResponse>

    @GET("account/${BuildConfig.MY_ID}/lists")
    suspend fun getCreatedList(
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = "ru-RU",
        @Query("session_id") sessionId: String,
        @Query("page") page: Int = 1
    ): Response<ListResponse>

    @GET("account/${BuildConfig.MY_ID}/favorite/movies")
    suspend fun getFavouriteMovieList(
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("session_id") sessionId: String,
        @Query("language") language: String = "ru-RU",
        @Query("sort_by") sortBy: String = "created_at.desc",
        @Query("page") page: Int = 1
    ): Response<FavouriteMovieList>

    @GET("account/${BuildConfig.MY_ID}/favorite/tv")
    suspend fun getFavouriteTVList(
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("session_id") sessionId: String,
        @Query("language") language: String = "ru-RU",
        @Query("sort_by") sortBy: String = "created_at.desc",
        @Query("page") page: Int = 1
    ): Response<FavouriteTVList>

    @GET("account/${BuildConfig.MY_ID}/watchlist/movies")
    suspend fun getMovieWatchlist(
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("session_id") sessionId: String,
        @Query("language") language: String = "ru-RU",
        @Query("sort_by") sortBy: String = "created_at.desc",
        @Query("page") page: Int = 1
    ): Response<MovieWatchList>

    @GET("account/${BuildConfig.MY_ID}/watchlist/tv")
    suspend fun getTVWatchlist(
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("session_id") sessionId: String,
        @Query("language") language: String = "ru-RU",
        @Query("sort_by") sortBy: String = "created_at.desc",
        @Query("page") page: Int = 1
    ): Response<TVWatchList>
}
