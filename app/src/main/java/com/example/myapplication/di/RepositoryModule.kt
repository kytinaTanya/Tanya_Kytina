package com.example.myapplication.di

import com.example.myapplication.repository.TmdbService
import com.example.myapplication.repository.AuthRepository
import com.example.myapplication.repository.Repository
import com.example.myapplication.repository.RepositoryImpl
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
    fun provideRepository(service: TmdbService) : Repository {
        return RepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(service: TmdbService) : AuthRepository {
        return RepositoryImpl(service)
    }
}