package com.example.myapplication.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.room.entity.Movie
import com.example.myapplication.room.entity.Movie.Companion.TABLE_NAME

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<Movie>)

    @Query("SELECT * FROM $TABLE_NAME ORDER BY `order`")
    suspend fun getAll(): List<Movie>

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun clearAll()
}