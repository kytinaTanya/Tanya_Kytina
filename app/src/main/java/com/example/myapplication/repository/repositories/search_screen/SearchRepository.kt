package com.example.myapplication.repository.repositories.search_screen

import com.example.myapplication.models.pojo.Film
import com.example.myapplication.models.pojo.TV

interface SearchRepository {
    suspend fun execute(query: String): SearchRepository.Result

    sealed class Result {
        data class Success(val movies: List<Film>, val tvs: List<TV>) : SearchRepository.Result()
        object ResultError : SearchRepository.Result()
        object ServerError : SearchRepository.Result()
    }
}