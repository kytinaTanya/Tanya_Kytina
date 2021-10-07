package com.example.myapplication.repository

import android.util.Log
import com.example.myapplication.models.history.CreatedListBody
import com.example.myapplication.models.history.MediaBody
import com.example.myapplication.models.history.PostResponseStatus
import com.example.myapplication.models.movies.Film

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
}