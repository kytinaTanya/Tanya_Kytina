package com.example.myapplication.repository.repositories

import com.example.myapplication.models.history.PostResponseStatus
import com.example.myapplication.models.images.ImageRequest
import com.example.myapplication.models.marks.AccountStates
import com.example.myapplication.models.movies.*

interface DetailsRepository {
    suspend fun getMovieDetails(id: Long): FilmDetails
    suspend fun getMoviesImages(id: Long): ImageData
    suspend fun getMoviesCredits(id: Long): CreditsResponse
    suspend fun getMovieVideos(id: Long): List<VideoResult>
    suspend fun getRecommendationMovies(id: Long): List<Film>
    suspend fun getSimilarMovies(id: Long): List<Film>
    suspend fun getTVDetails(id: Long): TvDetails
    suspend fun getTvsImages(id: Long): ImageData
    suspend fun getTvsCredits(id: Long): CreditsResponse
    suspend fun getTvVideos(id: Long): List<VideoResult>
    suspend fun getRecommendationTvs(id: Long): List<TV>
    suspend fun getSimilarTvs(id: Long): List<TV>
    suspend fun getPersonDetails(id: Long): PersonDetails
    suspend fun getPersonsImages(id: Long): List<ImageUrlPath>
    suspend fun getSeasonDetails(tvId: Long, seasonNum: Int): SeasonDetails
    suspend fun getEpisodeDetails(tvId: Long, seasonNum: Int, episodeNum: Int): EpisodeDetails
    suspend fun markAsFavorite(id: Long, type: String, mark: Boolean, sessionId: String): PostResponseStatus
    suspend fun addToWatchlist(id: Long, type: String, add: Boolean, sessionId: String): PostResponseStatus
    suspend fun rateMovie(id: Long, sessionId: String, rating: Float): PostResponseStatus
    suspend fun rateTv(id: Long, sessionId: String, rating: Float): PostResponseStatus
    suspend fun deleteMovieRating(id: Long, sessionId: String)
    suspend fun deleteTvRating(id: Long, sessionId: String)
    suspend fun getMovieAccountStates(id: Long, sessionId: String): AccountStates
    suspend fun getTvAccountStates(id: Long, sessionId: String): AccountStates
}