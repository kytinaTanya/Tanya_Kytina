package com.example.myapplication.repository

import androidx.paging.PagingData
import com.example.myapplication.movies.Movie
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getData() : Flow<PagingData<Movie>>
}