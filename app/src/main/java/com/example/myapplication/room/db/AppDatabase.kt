package com.example.myapplication.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myapplication.room.dao.MovieDao
import com.example.myapplication.room.entity.Movie
import com.example.myapplication.room.entity.Movie.Companion.TABLE_NAME

@Database(entities = [Movie::class], version = 2, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao() : MovieDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE $TABLE_NAME ADD COLUMN 'order' INTEGER DEFAULT 0 NOT NULL")
            }
        }
    }
}