package com.example.myapplication.repository.services

import com.example.myapplication.BuildConfig
import com.example.myapplication.models.RequestToken
import com.example.myapplication.models.RetrofitPostToken
import com.example.myapplication.models.SessionId
import com.example.myapplication.models.history.PostResponseStatus
import com.example.myapplication.models.lists.MovieList
import com.example.myapplication.models.marks.AddToWatchlistMovie
import com.example.myapplication.models.marks.MarkAsFavouriteMovie
import com.example.myapplication.models.marks.RatingValue
import com.example.myapplication.models.pojo.*
import com.google.gson.JsonElement
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
        @Query("language") language: String = BuildConfig.GENERAL_LANGUAGE
    ): Response<FilmDetails>

    @GET("movie/{movie_id}/images")
    suspend fun getMovieImages(
        @Path("movie_id") id: Long,
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH
    ): Response<ImageData>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") id: Long,
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = BuildConfig.GENERAL_LANGUAGE
    ): Response<CreditsResponse>

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendationMovies(
        @Path("movie_id") id: Long,
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = BuildConfig.GENERAL_LANGUAGE,
        @Query("page") page: Int = 1
    ): Response<MoviesResponse<Film>>

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") id: Long,
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = BuildConfig.GENERAL_LANGUAGE,
        @Query("page") page: Int = 1
    ): Response<MoviesResponse<Film>>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") id: Long,
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = BuildConfig.GENERAL_LANGUAGE
    ): Response<TrailerData>

    @GET("tv/{tv_id}")
    suspend fun getTvDetails(
        @Path("tv_id") id: Long,
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = BuildConfig.GENERAL_LANGUAGE
    ): Response<TvDetails>

    @GET("tv/{tv_id}/images")
    suspend fun getTvImages(
        @Path("tv_id") id: Long,
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH
    ): Response<ImageData>

    @GET("tv/{tv_id}/credits")
    suspend fun getTvCredits(
        @Path("tv_id") id: Long,
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = BuildConfig.GENERAL_LANGUAGE
    ): Response<CreditsResponse>

    @GET("tv/{tv_id}/recommendations")
    suspend fun getRecommendationTvs(
        @Path("tv_id") id: Long,
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = BuildConfig.GENERAL_LANGUAGE,
        @Query("page") page: Int = 1
    ): Response<MoviesResponse<TV>>

    @GET("tv/{tv_id}/similar")
    suspend fun getSimilarTvs(
        @Path("tv_id") id: Long,
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = BuildConfig.GENERAL_LANGUAGE,
        @Query("page") page: Int = 1
    ): Response<MoviesResponse<TV>>

    @GET("tv/{tv_id}/videos")
    suspend fun getTvVideos(
        @Path("tv_id") id: Long,
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = BuildConfig.GENERAL_LANGUAGE
    ): Response<TrailerData>

    @GET("person/{person_id}")
    suspend fun getPersonDetails(
        @Path("person_id") id: Long,
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = BuildConfig.GENERAL_LANGUAGE
    ): Response<PersonDetails>

    @GET("person/{person_id}/images")
    suspend fun getPersonImages(
        @Path("person_id") id: Long,
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH
    ): Response<ImageData>

    @GET("tv/{tv_id}/season/{season_number}")
    suspend fun getSeasonDetails(
        @Path("tv_id") tvId: Long,
        @Path("season_number") seasonNum: Int,
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = BuildConfig.GENERAL_LANGUAGE
    ) : Response<BaseItemDetails.SeasonDetails>

    @GET("tv/{tv_id}/season/{season_number}/episode/{episode_number}")
    suspend fun getEpisodeDetails(
        @Path("tv_id") tvId: Long,
        @Path("season_number") seasonNum: Int,
        @Path("episode_number") episodeNum: Int,
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = BuildConfig.GENERAL_LANGUAGE
    ): Response<BaseItemDetails.EpisodeDetails>

    @GET("collection/{collection_id}")
    suspend fun getCollectionDetails(
        @Path("collection_id") id: Int,
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = BuildConfig.GENERAL_LANGUAGE
    ): Response<BaseItemDetails.MovieCollection>

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
        @Query("language") language: String = BuildConfig.GENERAL_LANGUAGE,
        @Query("page") page: Int = 1,
        @Query("region") region: String = "RU"
    ): Response<MoviesResponse<Film>>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = BuildConfig.GENERAL_LANGUAGE,
        @Query("page") page: Int = 1,
        @Query("region") region: String = "RU"
    ): Response<MoviesResponse<Film>>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = BuildConfig.GENERAL_LANGUAGE,
        @Query("page") page: Int = 1,
        @Query("region") region: String = "RU"
    ): Response<MoviesResponse<Film>>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = BuildConfig.GENERAL_LANGUAGE,
        @Query("page") page: Int = 1
    ): Response<MoviesResponse<Film>>

    @GET("tv/popular")
    suspend fun getPopularTV(
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = BuildConfig.GENERAL_LANGUAGE,
        @Query("page") page: Int = 1
    ): Response<MoviesResponse<TV>>

    @GET("tv/airing_today")
    suspend fun getOnAirTodayTV(
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = BuildConfig.GENERAL_LANGUAGE,
        @Query("page") page: Int = 1
    ): Response<MoviesResponse<TV>>

    @GET("tv/on_the_air")
    suspend fun getNowOnAirTV(
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = BuildConfig.GENERAL_LANGUAGE,
        @Query("page") page: Int = 1
    ): Response<MoviesResponse<TV>>

    @GET("tv/top_rated")
    suspend fun getTopRatedTV(
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = BuildConfig.GENERAL_LANGUAGE,
        @Query("page") page: Int = 1
    ): Response<MoviesResponse<TV>>

    @GET("person/popular")
    suspend fun getPopularPersons(
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = BuildConfig.GENERAL_LANGUAGE,
        @Query("page") page: Int = 1
    ): Response<MoviesResponse<Person>>

    @GET("account/{account_id}/favorite/movies")
    suspend fun getFavouriteMovieList(
        @Path("account_id") id: Int = BuildConfig.MY_ID.toInt(),
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("session_id") sessionId: String,
        @Query("language") language: String = BuildConfig.GENERAL_LANGUAGE,
        @Query("sort_by") sortBy: String = "created_at.desc",
        @Query("page") page: Int = 1
    ): Response<MovieList.FavouriteMovieList>

    @GET("account/{account_id}/favorite/tv")
    suspend fun getFavouriteTVList(
        @Path("account_id") id: Int = BuildConfig.MY_ID.toInt(),
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("session_id") sessionId: String,
        @Query("language") language: String = BuildConfig.GENERAL_LANGUAGE,
        @Query("sort_by") sortBy: String = "created_at.desc",
        @Query("page") page: Int = 1
    ): Response<MovieList.FavouriteTVList>

    @GET("account/{account_id}/watchlist/movies")
    suspend fun getMovieWatchlist(
        @Path("account_id") id: Int = BuildConfig.MY_ID.toInt(),
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("session_id") sessionId: String,
        @Query("language") language: String = BuildConfig.GENERAL_LANGUAGE,
        @Query("sort_by") sortBy: String = "created_at.desc",
        @Query("page") page: Int = 1
    ): Response<MovieList.MovieWatchList>

    @GET("account/{account_id}/watchlist/tv")
    suspend fun getTVWatchlist(
        @Path("account_id") id: Int = BuildConfig.MY_ID.toInt(),
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("session_id") sessionId: String,
        @Query("language") language: String = BuildConfig.GENERAL_LANGUAGE,
        @Query("sort_by") sortBy: String = "created_at.desc",
        @Query("page") page: Int = 1
    ): Response<MovieList.TVWatchList>

    @GET("account/{account_id}/rated/movies")
    suspend fun ratedMovies(
       @Path("account_id") id: Int = BuildConfig.MY_ID.toInt(),
       @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
       @Query("language") language: String = BuildConfig.GENERAL_LANGUAGE,
       @Query("session_id") sessionId: String,
       @Query("sort_by") sortBy: String = "created_at.desc",
       @Query("page") page: Int = 1
    ): Response<MoviesResponse<Film>>

    @GET("account/{account_id}/rated/tv")
    suspend fun ratedTvs(
        @Path("account_id") id: Int = BuildConfig.MY_ID.toInt(),
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = BuildConfig.GENERAL_LANGUAGE,
        @Query("session_id") sessionId: String,
        @Query("sort_by") sortBy: String = "created_at.desc",
        @Query("page") page: Int = 1
    ): Response<MoviesResponse<TV>>

    @GET("account/{account_id}/rated/tv/episodes")
    suspend fun ratedEpisodes(
        @Path("account_id") id: Int = BuildConfig.MY_ID.toInt(),
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = BuildConfig.GENERAL_LANGUAGE,
        @Query("session_id") sessionId: String,
        @Query("sort_by") sortBy: String = "created_at.desc",
        @Query("page") page: Int = 1
    ): Response<MoviesResponse<Episode>>

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = BuildConfig.GENERAL_LANGUAGE,
        @Query("query") query: String = "",
        @Query("page") page: Int = 1,
        @Query("include_adult") include: Boolean = false
    ): Response<MoviesResponse<Film>>

    @GET("search/tv")
    suspend fun searchTvs(
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = BuildConfig.GENERAL_LANGUAGE,
        @Query("query") query: String = "",
        @Query("page") page: Int = 1,
        @Query("include_adult") include: Boolean = false
    ): Response<MoviesResponse<TV>>

    @POST("account/{account_id}/favorite")
    suspend fun markAsFavourite(
        @Path("account_id") id: Int = BuildConfig.MY_ID.toInt(),
        @Header("Content-Type") contextType: String = BuildConfig.HEADER,
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("session_id") sessionId: String,
        @Body body: MarkAsFavouriteMovie
    ): Response<PostResponseStatus>

    @POST("account/{account_id}/watchlist")
    suspend fun addToWatchlist(
        @Path("account_id") id: Int = BuildConfig.MY_ID.toInt(),
        @Header("Content-Type") contextType: String = BuildConfig.HEADER,
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("session_id") sessionId: String,
        @Body body: AddToWatchlistMovie
    ): Response<PostResponseStatus>

    @GET("movie/{movie_id}/account_states")
    suspend fun getMovieAccountStates(
        @Path("movie_id") id: Long,
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("session_id") sessionId: String
    ): JsonElement

    @GET("tv/{tv_id}/season/{season_number}/episode/{episode_number}/account_states")
    suspend fun getEpisodeAccountStates(
        @Path("tv_id") tvId: Long,
        @Path("season_number") season: Int,
        @Path("episode_number") episode: Int,
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = BuildConfig.GENERAL_LANGUAGE,
        @Query("session_id") sessionId: String
    ): JsonElement

    @GET("tv/{tv_id}/account_states")
    suspend fun getTvAccountStates(
        @Path("tv_id") id: Long,
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("language") language: String = BuildConfig.GENERAL_LANGUAGE,
        @Query("session_id") sessionId: String
    ): JsonElement

    @POST("movie/{movie_id}/rating")
    suspend fun rateMovie(
        @Path("movie_id") id: Long,
        @Header("Content-Type") contextType: String = BuildConfig.HEADER,
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("session_id") sessionId: String,
        @Body body: RatingValue
    ): Response<PostResponseStatus>

    @DELETE("movie/{movie_id}/rating")
    suspend fun deleteMovieRating(
        @Path("movie_id") id: Long,
        @Header("Content-Type") contextType: String = BuildConfig.HEADER,
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("session_id") sessionId: String,
    ): Response<PostResponseStatus>

    @POST("tv/{tv_id}/rating")
    suspend fun rateTv(
        @Path("tv_id") id: Long,
        @Header("Content-Type") contextType: String = BuildConfig.HEADER,
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("session_id") sessionId: String,
        @Body body: RatingValue
    ): Response<PostResponseStatus>

    @DELETE("tv/{tv_id}/rating")
    suspend fun deleteTvRating(
        @Path("tv_id") id: Long,
        @Header("Content-Type") contextType: String = BuildConfig.HEADER,
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("session_id") sessionId: String
    ): Response<PostResponseStatus>

    @POST("tv/{tv_id}/season/{season_number}/episode/{episode_number}/rating")
    suspend fun rateEpisode(
        @Path("tv_id") tvId: Long,
        @Path("season_number") season: Int,
        @Path("episode_number") episode: Int,
        @Header("Content-Type") contextType: String = BuildConfig.HEADER,
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("session_id") sessionId: String,
        @Body body: RatingValue
    ): Response<PostResponseStatus>

    @DELETE("tv/{tv_id}/season/{season_number}/episode/{episode_number}/rating")
    suspend fun deleteEpisodeRating(
        @Path("tv_id") tvId: Long,
        @Path("season_number") season: Int,
        @Path("episode_number") episode: Int,
        @Header("Content-Type") contextType: String = BuildConfig.HEADER,
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH,
        @Query("session_id") sessionId: String
    ): Response<PostResponseStatus>
}
