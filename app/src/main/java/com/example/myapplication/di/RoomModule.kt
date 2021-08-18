package com.example.myapplication.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.room.dao.MovieDao
import com.example.myapplication.room.db.AppDatabase
import com.example.myapplication.room.db.AppDatabase.Companion.MIGRATION_1_2
import dagger.Module
import dagger.Provides

@Module
class RoomModule {
    @Provides
    fun provideDb(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "movie_database"
        ).addMigrations(MIGRATION_1_2)
            .build()
    }

    @Provides
    fun providesDao(db: AppDatabase): MovieDao {
        return db.movieDao()
    }
}