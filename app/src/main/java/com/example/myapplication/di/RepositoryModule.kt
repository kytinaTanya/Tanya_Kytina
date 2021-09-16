package com.example.myapplication.di

import com.example.myapplication.movies.TmdbService
import com.example.myapplication.repository.AuthRepository
import com.example.myapplication.repository.Repository
import com.example.myapplication.repository.RepositoryImpl
import com.example.myapplication.room.dao.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(service: TmdbService, dao: MovieDao) : Repository {
        return RepositoryImpl(service, dao)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(service: TmdbService, dao: MovieDao) : AuthRepository {
        return RepositoryImpl(service, dao)
    }
}