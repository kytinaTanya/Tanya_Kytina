package com.example.myapplication.repository

import com.example.myapplication.movies.Movie

interface Repository {

    fun getData(onSuccess: (List<Movie>) -> Unit, onError: () -> Unit)
}