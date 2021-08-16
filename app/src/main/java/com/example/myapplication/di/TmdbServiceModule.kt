package com.example.myapplication.di

import com.example.myapplication.movies.TmdbService
import dagger.Module
import dagger.Provides

@Module
class TmdbServiceModule {

    @Provides
    fun provideTmdbService(): TmdbService {
        return TmdbService.createApiService()
    }

}