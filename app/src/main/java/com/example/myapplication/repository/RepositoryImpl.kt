package com.example.myapplication.repository

import android.util.Log
import com.example.myapplication.models.Film
import com.example.myapplication.models.RetrofitPostToken
import com.example.myapplication.models.Movie
import com.example.myapplication.models.TV
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val service: TmdbService) : Repository, AuthRepository {

    override suspend fun getListOfPopularMovies() : List<Film> {
        val response = service.getPopularMovies()
        return if (response.isSuccessful) {
            val responseBody = response.body()
            responseBody?.movies ?: emptyList()
        } else {
            Log.d("Repo", "response is not successful")
            emptyList()
        }
    }

    override suspend fun getListOfRecommendations(): List<Film> {
        val response = service.getRecommendationsMovies()
        return if(response.isSuccessful) {
            response.body()?.movies ?: emptyList()
        } else {
            emptyList()
        }
    }

    override suspend fun getListOfLatestMovies(): List<Film> {
        val response = service.getLatestMovies()
        return if(response.isSuccessful) {
            response.body()?.movies ?: emptyList()
        } else {
            emptyList()
        }
    }

    override suspend fun getMovieDetails(id: Long): Film {
        val response = service.getMovieDetails(id = id, language = "ru-RU")

        return if(response.isSuccessful) {
            response.body() ?: Film(1, "Non", "Non", "Non", "Non", 0.0F, "Non")
        } else {
            Log.d("Repo", "response is not successful")
            Film(1, "Non", "Non", "Non", "Non", 0.0F, "Non")
        }
    }

    override suspend fun getPopularTv(): List<TV> {
        val response = service.getPopularTV()
        return if(response.isSuccessful) {
            response.body()?.TV ?: emptyList()
        } else {
            emptyList()
        }
    }

    override suspend fun getRequestToken(): String {
        val response = service.getRequestToken()

        return if(response.isSuccessful) {
            val req = response.body()?.requestToken ?: EMPTY_STRING
            Log.d("REQUESTTOKEN", req)
            req
        } else {
            EMPTY_STRING
        }
    }

    override suspend fun createSessionId(requestToken: String): String {
        if(requestToken == EMPTY_STRING) {
            Log.d("TOKEN", "Not found")
            return EMPTY_STRING
        }

        val body = RetrofitPostToken(requestToken)

        val response = service.postSession(body = body)
        Log.d("POSTRESPONSE", response.message())

        return if(response.isSuccessful) {
            response.body()?.sessionId ?: EMPTY_STRING
        } else {
            EMPTY_STRING
        }
    }

    companion object {
        val EMPTY_STRING = ""
    }
}