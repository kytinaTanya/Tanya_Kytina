package com.example.myapplication.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.movies.Movie
import com.example.myapplication.room.entity.MovieEntity

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<Movie>)

    @Query("SELECT * FROM ${Movie.TABLE_NAME} ORDER BY :rating DESC")
    fun pagingSource(rating: Float): PagingSource<Int, Movie>

    @Query("DELETE FROM ${Movie.TABLE_NAME}")
    suspend fun clearAll()
}