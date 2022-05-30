package com.example.myapplication.repository.repositories

import com.example.myapplication.models.pojo.Film
import com.example.myapplication.models.pojo.Person
import com.example.myapplication.models.pojo.TV

interface Repository {
    suspend fun getListOfPopularMovies() : Result
    suspend fun getListOfNowPlayingMovies() : Result
    suspend fun getListOfUpcomingMovies() : Result
    suspend fun getListOfTopRatedMovies() : Result
    suspend fun getListOfPopularTv() : Result
    suspend fun getListOfOnAirTodayTV() : Result
    suspend fun getListOfNowOnAirTV() : Result
    suspend fun getListOfTopRatedTV() : Result
    suspend fun getListOfPopularPersons() : Result

    sealed class Result {
        sealed class Success : Repository.Result() {
            data class FilmSuccess(val list: List<Film>) : Success()
            data class TvSuccess(val list: List<TV>) : Success()
            data class PersonSuccess(val list: List<Person>) : Success()
        }
        object Error : Repository.Result()
    }
}