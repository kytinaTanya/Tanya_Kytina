package com.example.myapplication.repository

import com.example.myapplication.movies.Movie

interface Repository {
    suspend fun getListOfMovies() : List<Movie>
    suspend fun getMovieDetails(id: Long) : Movie
}