package com.example.myapplication.repository

import com.example.myapplication.models.movies.Film
import com.example.myapplication.models.movies.Person
import com.example.myapplication.models.movies.TV

class DetailsRepositoryImpl(val service: TmdbService): DetailsRepository {
    override suspend fun getMovieDetails(id: Long): Film {
        val response = service.getMovieDetails(id = id, language = "ru-RU")
        return if(response.isSuccessful) {
            response.body() ?: Film()
        } else {
            Film()
        }
    }

    override suspend fun getTVDetails(id: Long): TV {
        val response = service.getTvDetails(id = id, language = "ru-RU")
        return if(response.isSuccessful) {
            response.body() ?: TV()
        } else {
            TV()
        }
    }

    override suspend fun getPersonDetails(id: Long): Person {
        val response = service.getPersonDetails(id = id, language = "ru-RU")
        return if(response.isSuccessful) {
            response.body() ?: Person()
        } else {
            Person()
        }
    }
}