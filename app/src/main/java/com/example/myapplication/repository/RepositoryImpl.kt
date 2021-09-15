package com.example.myapplication.repository

import android.util.Log
import com.example.myapplication.models.RetrofitPostToken
import com.example.myapplication.room.entity.Movie
import com.example.myapplication.movies.TmdbService
import com.example.myapplication.room.dao.MovieDao
import com.example.myapplication.room.entity.Movie.Companion.TABLE_SIZE

class RepositoryImpl(private val service: TmdbService, private val dao: MovieDao) : Repository, AuthRepository {

    override suspend fun getListOfMovies() : List<Movie> {
        // получение данных позже отрефакторить нужно под коррутины и RX
        val response = service.getTopRatedMovies(language = "ru-RU")

        val localResponse = dao.getAll()
        return if(localResponse.isNotEmpty()) {
            localResponse
        } else {
            if (response.isSuccessful) {
                val responseBody = response.body()
                val list = responseBody?.movies?.map {
                    Movie(
                        id = it.id,
                        title = it.title,
                        overview = it.overview,
                        posterPath = it.posterPath,
                        backdropPath = it.backdropPath,
                        releaseDate = it.releaseDate,
                        rating = it.rating,
                        order = TABLE_SIZE++
                    )
                } ?: emptyList()
                dao.insertAll(list)
                list
            } else {
                Log.d("Repo", "response is not successful")
                emptyList()
            }
        }
    }

    override suspend fun getMovieDetails(id: Long): Movie {
        val response = service.getMovieDetails(id = id, language = "ru-RU")

        return if(response.isSuccessful) {
            response.body() ?: Movie(1, "Non", "Non", "Non", "Non", 0.0F, "Non")
        } else {
            Log.d("Repo", "response is not successful")
            Movie(1, "Non", "Non", "Non", "Non", 0.0F, "Non")
        }
    }

    override suspend fun getRequestToken(): String {
        val response = service.getRequestToken()

        return if(response.isSuccessful) {
            response.body()?.requestToken ?: EMPTY_STRING
        } else {
            EMPTY_STRING
        }
    }

    override suspend fun createSessionId(): String {
        val requestToken = getRequestToken()

        if(requestToken == EMPTY_STRING) {
            Log.d("TOKEN", "Not found")
            return ""
        }

        val body = RetrofitPostToken(requestToken)

        val response = service.postSession(body = body)

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