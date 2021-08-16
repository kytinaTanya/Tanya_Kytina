package com.example.myapplication.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.room.entity.MovieEntity.Companion.TABLE_NAME
import com.google.gson.annotations.SerializedName

@Entity(tableName = TABLE_NAME)
data class MovieEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "posterPath") val posterPath: String,
    @ColumnInfo(name = "backdropPath") val backdropPath: String,
    @ColumnInfo(name = "rating") val rating: Float,
    @ColumnInfo(name = "releaseDate") val releaseDate: String
    ) {
    companion object {
        const val TABLE_NAME = "movie_table"
    }
}
