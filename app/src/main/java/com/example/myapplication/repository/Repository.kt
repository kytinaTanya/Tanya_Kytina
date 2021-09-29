package com.example.myapplication.repository

import com.example.myapplication.models.Film
import com.example.myapplication.models.TV

interface Repository {
    suspend fun getListOfPopularMovies() : List<Film>
    suspend fun getListOfRecommendations() : List<Film>
    suspend fun getListOfLatestMovies() : List<Film>
    suspend fun getMovieDetails(id: Long) : Film
    suspend fun getPopularTv() : List<TV>
}