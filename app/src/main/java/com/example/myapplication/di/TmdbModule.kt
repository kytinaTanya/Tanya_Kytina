package com.example.myapplication.di

import com.example.myapplication.movies.TmdbService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TmdbModule {
    @Provides
    @Singleton
    fun provideTmdbService(): TmdbService {
        return TmdbService.createApiService()
    }
}