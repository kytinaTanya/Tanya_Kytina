package com.example.myapplication.di

import com.example.myapplication.movies.TmdbService
import com.example.myapplication.repository.Repository
import com.example.myapplication.repository.RepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideRepository(service: TmdbService): Repository {
        return RepositoryImpl(service)
    }

}
