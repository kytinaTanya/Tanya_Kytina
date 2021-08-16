package com.example.myapplication.repository.inMemory

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.myapplication.movies.Movie
import com.example.myapplication.movies.TmdbService
import com.example.myapplication.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val service: TmdbService) : Repository {
    override fun getData(): Flow<PagingData<Movie>> = Pager(
        PagingConfig(20, 5)
    ) {
        MoviePagingSource(service)
    }.flow
}