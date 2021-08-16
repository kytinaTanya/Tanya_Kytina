package com.example.myapplication.di

import com.example.myapplication.movies.TmdbService
import com.example.myapplication.repository.inMemory.MoviePagingSource
import dagger.Module
import dagger.Provides


@Module
class PagingSourceModule {

    @Provides
    fun providePagingSource(tmdbService: TmdbService) : MoviePagingSource {
        return MoviePagingSource(tmdbService)
    }
}