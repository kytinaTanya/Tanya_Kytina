package com.example.myapplication.repository

import com.example.myapplication.room.entity.Movie

interface Repository {
    suspend fun getListOfMovies() : List<Movie>
    suspend fun getMovieDetails(id: Long) : Movie
}