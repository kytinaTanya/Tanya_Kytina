package com.example.myapplication.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.room.db.MovieDb
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MovieDbModule {
    @Singleton
    @Provides
    fun providesRoomDb(context: Context) = Room.databaseBuilder(
            context,
            MovieDb::class.java,
            "movie_base"
        ).build()

}