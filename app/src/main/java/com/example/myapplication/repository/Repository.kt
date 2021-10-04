package com.example.myapplication.repository

import com.example.myapplication.models.movies.Film
import com.example.myapplication.models.movies.Person
import com.example.myapplication.models.movies.TV

interface Repository: DetailsRepository {
    suspend fun getListOfPopularMovies() : List<Film>
    suspend fun getListOfNowPlayingMovies() : List<Film>
    suspend fun getListOfUpcomingMovies() : List<Film>
    suspend fun getListOfTopRatedMovies() : List<Film>
    suspend fun getListOfPopularTv() : List<TV>
    suspend fun getListOfOnAirTodayTV() : List<TV>
    suspend fun getListOfNowOnAirTV() : List<TV>
    suspend fun getListOfTopRatedTV() : List<TV>
    suspend fun getListOfPopularPersons() : List<Person>
}