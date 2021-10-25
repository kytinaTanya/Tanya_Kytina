package com.example.myapplication.repository.impl

import android.util.Log
import com.example.myapplication.models.movies.Film
import com.example.myapplication.models.RetrofitPostToken
import com.example.myapplication.models.lists.*
import com.example.myapplication.models.movies.Person
import com.example.myapplication.models.movies.TV
import com.example.myapplication.repository.repositories.AuthRepository
import com.example.myapplication.repository.repositories.ListRepository
import com.example.myapplication.repository.repositories.Repository
import com.example.myapplication.repository.services.TmdbService
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val service: TmdbService) : Repository,
    AuthRepository,
    ListRepository {

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

    override suspend fun getListOfNowPlayingMovies(): List<Film> {
        val response = service.getNowPlayingMovies()
        return if(response.isSuccessful) {
            response.body()?.movies ?: emptyList()
        } else {
            emptyList()
        }
    }

    override suspend fun getListOfUpcomingMovies(): List<Film> {
        val response = service.getUpcomingMovies()
        return if(response.isSuccessful) {
            response.body()?.movies ?: emptyList()
        } else {
            emptyList()
        }
    }

    override suspend fun getListOfTopRatedMovies(): List<Film> {
        val response = service.getTopRatedMovies()
        return if(response.isSuccessful) {
            response.body()?.movies ?: emptyList()
        } else {
            emptyList()
        }
    }

    override suspend fun getListOfPopularTv(): List<TV> {
        val response = service.getPopularTV()
        return if(response.isSuccessful) {
            response.body()?.movies ?: emptyList()
        } else {
            emptyList()
        }
    }

    override suspend fun getListOfOnAirTodayTV(): List<TV> {
        val response = service.getOnAirTodayTV()
        return if(response.isSuccessful) {
            response.body()?.movies ?: emptyList()
        } else {
            emptyList()
        }
    }

    override suspend fun getListOfNowOnAirTV(): List<TV> {
        val response = service.getNowOnAirTV()
        return if(response.isSuccessful) {
            response.body()?.movies ?: emptyList()
        } else {
            emptyList()
        }
    }

    override suspend fun getListOfTopRatedTV(): List<TV> {
        val response = service.getTopRatedTV()
        return if(response.isSuccessful) {
            response.body()?.movies ?: emptyList()
        } else {
            emptyList()
        }
    }

    override suspend fun getListOfPopularPersons(): List<Person> {
        val response = service.getPopularPersons()
        return if(response.isSuccessful) {
            response.body()?.movies ?: emptyList()
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

    override suspend fun getCreatedLists(sessionId: String): List<CreatedList> {
        val response = service.getCreatedList(sessionId = sessionId)
        return if(response.isSuccessful) {
            response.body()?.result ?: emptyList()
        } else {
            emptyList()
        }
    }

    override suspend fun getFavoriteMoviesList(sessionId: String): FavouriteMovieList? {
        val response = service.getFavouriteMovieList(sessionId = sessionId)
        return if(response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    override suspend fun getFavoriteTVsList(sessionId: String): FavouriteTVList? {
        val response = service.getFavouriteTVList(sessionId = sessionId)
        return if(response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    override suspend fun getMovieWatchlist(sessionId: String): MovieWatchList? {
        val response = service.getMovieWatchlist(sessionId = sessionId)
        return if(response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    override suspend fun getTVWatchlist(sessionId: String): TVWatchList? {
        val response = service.getTVWatchlist(sessionId = sessionId)
        return if(response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}