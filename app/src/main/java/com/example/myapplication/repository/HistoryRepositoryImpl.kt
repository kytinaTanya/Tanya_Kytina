package com.example.myapplication.repository

import android.app.DownloadManager
import android.util.Log
import com.example.myapplication.models.history.CreatedListBody
import com.example.myapplication.models.history.MediaBody
import com.example.myapplication.models.history.PostResponseStatus
import com.example.myapplication.models.movies.Episode
import com.example.myapplication.models.movies.Film
import com.example.myapplication.models.movies.Movie
import com.example.myapplication.models.movies.TV

class HistoryRepositoryImpl(val service: TmdbService): HistoryRepository {
    override suspend fun getDetailsAboutHistoryList(id: Int, sessionId: String): List<Film> {
        val response = service.detailsAboutHistoryList(id = id, sessionId = sessionId)
        return if(response.isSuccessful) {
            response.body()?.items ?: emptyList()
        } else {
            emptyList()
        }
    }

    override suspend fun createList(sessionId: String): Int {
        val response = service.createList(sessionId = sessionId, body = CreatedListBody())
        if(response.isSuccessful) {
            Log.d("HistoryRepository", "Ok: ${response.body()?.id}")
            return response.body()?.id ?: 0
        } else {
            Log.d("HistoryRepository", "${response.body()?.message}")
            return 0
        }
    }

    override suspend fun addMovie(id: Int, sessionId: String, mediaId: Long): PostResponseStatus {
        val response = service.addMovie(id = id, sessionId = sessionId, body = MediaBody(mediaId))
        return if(response.isSuccessful) {
            response.body() ?: PostResponseStatus()
        } else {
            PostResponseStatus()
        }
    }

    override suspend fun removeMovie(id: Int, sessionId: String, mediaId: Long): PostResponseStatus {
        val response = service.removeMovie(id = id, sessionId = sessionId, body = MediaBody(mediaId))
        return if(response.isSuccessful) {
            response.body() ?: PostResponseStatus()
        } else {
            PostResponseStatus()
        }
    }

    override suspend fun getRatedMovies(sessionId: String): List<Film> {
        val response = service.ratedMovies(sessionId = sessionId)
        return if(response.isSuccessful) {
            response.body()?.movies ?: emptyList()
        } else {
            emptyList()
        }
    }

    override suspend fun getRatedTvs(sessionId: String): List<TV> {
        val response = service.ratedTvs(sessionId = sessionId)
        return if(response.isSuccessful) {
            response.body()?.movies ?: emptyList()
        } else {
            emptyList()
        }
    }

    override suspend fun getRatedEpisodes(sessionId: String): List<Episode> {
        val response = service.ratedEpisodes(sessionId = sessionId)
        return if(response.isSuccessful) {
            response.body()?.movies ?: emptyList()
        } else {
            emptyList()
        }
    }

    override suspend fun searchMovie(query: String): List<Film> {
        val response = service.searchMovies(query = query)
        return if(response.isSuccessful) {
            response.body()?.movies ?: emptyList()
        } else {
            emptyList()
        }
    }
}