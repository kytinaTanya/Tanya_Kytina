package com.example.myapplication.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.room.entity.Movie.Companion.TABLE_NAME
import com.google.gson.annotations.SerializedName

@Entity(tableName = TABLE_NAME)
data class Movie(
    @PrimaryKey
    @SerializedName("id")
    val id: Long,

    @SerializedName("title")
    val title: String,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("backdrop_path")
    val backdropPath: String,

    @SerializedName("vote_average")
    val rating: Float,

    @SerializedName("release_date")
    val releaseDate: String,

    val order: Int = 0
) {
    fun releaseYear(): String{
        return releaseDate.substringBefore("-")
    }

    companion object {
        const val TABLE_NAME = "movie_table"
        var TABLE_SIZE = 1
    }
}
