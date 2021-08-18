package com.example.myapplication.di

import com.example.myapplication.movies.TmdbService
import com.example.myapplication.repository.Repository
import com.example.myapplication.repository.RepositoryImpl
import com.example.myapplication.room.dao.MovieDao
import com.example.myapplication.room.db.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideRepository(service: TmdbService, dao: MovieDao): Repository {
        return RepositoryImpl(service, dao)
    }

}
