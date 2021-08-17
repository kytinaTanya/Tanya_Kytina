package com.example.myapplication.repository

import android.util.Log
import com.example.myapplication.movies.Movie
import com.example.myapplication.movies.MoviesResponse
import com.example.myapplication.movies.TmdbService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryImpl(private val service: TmdbService) : Repository {

    override suspend fun getListOfMovies() : List<Movie> {
        // получение данных позже отрефакторить нужно под коррутины и RX
        val response = service.getTopRatedMovies(language = "ru-RU")

        return if(response.isSuccessful){
            val responseBody = response.body()
            responseBody?.movies ?: emptyList()
        } else {
            Log.d("Repo", "response is not successful")
            emptyList()
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
}