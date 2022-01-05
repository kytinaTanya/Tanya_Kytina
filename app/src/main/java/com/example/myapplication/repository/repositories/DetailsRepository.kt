package com.example.myapplication.repository.repositories

import com.example.myapplication.models.history.PostResponseStatus
import com.example.myapplication.models.pojo.EpisodeDetails
import com.example.myapplication.models.pojo.MovieCollection
import com.example.myapplication.models.pojo.SeasonDetails
import com.example.myapplication.models.pojo.view.MovieView
import com.example.myapplication.models.pojo.view.PersonView
import com.example.myapplication.models.pojo.view.TvView

interface DetailsRepository {
    suspend fun getMovieView(id: Long, sessionId: String): MovieView
    suspend fun getTVView(id: Long, sessionId: String): TvView
    suspend fun getPersonView(id: Long): PersonView
    suspend fun getSeasonDetails(tvId: Long, seasonNum: Int): SeasonDetails
    suspend fun getEpisodeDetails(tvId: Long, seasonNum: Int, episodeNum: Int): EpisodeDetails
    suspend fun getCollectionsDetails(id: Int): MovieCollection
    suspend fun markAsFavorite(id: Long, type: String, mark: Boolean, sessionId: String): PostResponseStatus
    suspend fun addToWatchlist(id: Long, type: String, add: Boolean, sessionId: String): PostResponseStatus
    suspend fun rateMovie(id: Long, sessionId: String, rating: Float): PostResponseStatus
    suspend fun rateTv(id: Long, sessionId: String, rating: Float): PostResponseStatus
    suspend fun deleteMovieRating(id: Long, sessionId: String)
    suspend fun deleteTvRating(id: Long, sessionId: String)
}