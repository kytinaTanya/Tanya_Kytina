package com.example.myapplication.di

import com.example.myapplication.movies.TmdbService
import com.example.myapplication.repository.inDb.MovieRemoteMediator
import com.example.myapplication.room.db.MovieDb
import dagger.Module
import dagger.Provides

@Module
class RemoteMediatorModule {
    @Provides
    fun provideRemoteMediator(db: MovieDb, service: TmdbService) : MovieRemoteMediator {
        return MovieRemoteMediator(db, service)
    }
}