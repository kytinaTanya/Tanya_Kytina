package com.example.myapplication.repository

import com.example.myapplication.models.history.PostResponseStatus
import com.example.myapplication.models.movies.Episode
import com.example.myapplication.models.movies.Film
import com.example.myapplication.models.movies.Movie
import com.example.myapplication.models.movies.TV

interface HistoryRepository {
    suspend fun getDetailsAboutHistoryList(id: Int, sessionId: String): List<Film>
    suspend fun createList(sessionId: String): Int
    suspend fun addMovie(id: Int, sessionId: String, mediaId: Long): PostResponseStatus
    suspend fun removeMovie(id: Int, sessionId: String, mediaId: Long): PostResponseStatus
    suspend fun getRatedMovies(sessionId: String): List<Film>
    suspend fun getRatedTvs(sessionId: String): List<TV>
    suspend fun getRatedEpisodes(sessionId: String): List<Episode>
    suspend fun searchMovie(query: String): List<Film>
}