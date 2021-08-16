package com.example.myapplication.di

import com.example.myapplication.movies.TmdbService
import com.example.myapplication.repository.Repository
import com.example.myapplication.repository.inDb.MovieRepository
import com.example.myapplication.room.db.MovieDb
import dagger.Module
import dagger.Provides

@Module
class RepositoryDbModule {
    @Provides
    fun providesRepository(db: MovieDb, service: TmdbService) : Repository {
        return MovieRepository(db, service)
    }
}