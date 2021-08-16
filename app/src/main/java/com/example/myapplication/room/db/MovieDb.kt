package com.example.myapplication.room.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.movies.Movie
import com.example.myapplication.room.dao.MovieDao
import com.example.myapplication.room.dao.PageDao
import com.example.myapplication.room.entity.MovieEntity
import com.example.myapplication.room.entity.PageEntity

@Database(
    entities = [Movie::class, PageEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDb : RoomDatabase() {

    abstract fun moviesDao(): MovieDao
    abstract fun keysDao() : PageDao
}