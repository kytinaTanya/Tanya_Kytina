package com.example.myapplication.di

import com.example.myapplication.repository.*
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

    @Provides
    @Singleton
    fun provideListRepository(service: TmdbService) : ListRepository {
        return RepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun provideDetailsRepository(service: TmdbService) : DetailsRepository {
        return DetailsRepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun provideHistoryRepository(service: TmdbService) : HistoryRepository {
        return HistoryRepositoryImpl(service)
    }
}