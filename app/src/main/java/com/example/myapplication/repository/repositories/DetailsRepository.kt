package com.example.myapplication.repository.repositories

import com.example.myapplication.models.history.PostResponseStatus
import com.example.myapplication.models.marks.AccountStates
import com.example.myapplication.models.movies.Episode
import com.example.myapplication.models.movies.Film
import com.example.myapplication.models.movies.Person
import com.example.myapplication.models.movies.TV

interface DetailsRepository {
    suspend fun getMovieDetails(id: Long) : Film
    suspend fun getTVDetails(id: Long) : TV
    suspend fun getPersonDetails(id: Long): Person
    suspend fun getEpisodeDetails(tvId: Long, seasonNum: Int, episodeNum: Int): Episode
    suspend fun markAsFavorite(id: Long, type: String, mark: Boolean, sessionId: String): PostResponseStatus
    suspend fun addToWatchlist(id: Long, type: String, add: Boolean, sessionId: String): PostResponseStatus
    suspend fun rateMovie(id: Long, sessionId: String, rating: Float): PostResponseStatus
    suspend fun rateTv(id: Long, sessionId: String, rating: Float): PostResponseStatus
    suspend fun deleteMovieRating(id: Long, sessionId: String)
    suspend fun deleteTvRating(id: Long, sessionId: String)
    suspend fun getMovieAccountStates(id: Long, sessionId: String): AccountStates
    suspend fun getTvAccountStates(id: Long, sessionId: String): AccountStates
}