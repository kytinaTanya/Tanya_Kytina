package com.example.myapplication.repository

import com.example.myapplication.models.movies.Film
import com.example.myapplication.models.movies.Person
import com.example.myapplication.models.movies.TV

interface DetailsRepository {
    suspend fun getMovieDetails(id: Long) : Film
    suspend fun getTVDetails(id: Long) : TV
    suspend fun getPersonDetails(id: Long): Person
}