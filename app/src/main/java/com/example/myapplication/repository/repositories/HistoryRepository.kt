package com.example.myapplication.repository.repositories

import com.example.myapplication.models.history.PostResponseStatus
import com.example.myapplication.models.pojo.Episode
import com.example.myapplication.models.pojo.Film
import com.example.myapplication.models.pojo.TV

interface HistoryRepository {
    suspend fun getDetailsAboutHistoryList(id: Int, sessionId: String): List<Film>
    suspend fun createList(sessionId: String): Int
    suspend fun addMovie(id: Int, sessionId: String, mediaId: Long): PostResponseStatus
    suspend fun removeMovie(id: Int, sessionId: String, mediaId: Long): PostResponseStatus
    suspend fun getRatedMovies(sessionId: String): List<Film>
    suspend fun getRatedTvs(sessionId: String): List<TV>
    suspend fun getRatedEpisodes(sessionId: String): List<Episode>
    suspend fun searchMovie(query: String): List<Film>
    suspend fun searchTV(query: String): List<TV>
}