package com.example.myapplication.movies

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.room.entity.MovieEntity
import com.google.gson.annotations.SerializedName

@Entity(tableName = Movie.TABLE_NAME)
data class Movie(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Long,

    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String,

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    val overview: String,

    @ColumnInfo(name = "posterPath")
    @SerializedName("poster_path")
    val posterPath: String,

    @ColumnInfo(name = "backdropPath")
    @SerializedName("backdrop_path")
    val backdropPath: String,

    @ColumnInfo(name = "rating")
    @SerializedName("vote_average")
    val rating: Float,

    @ColumnInfo(name = "releaseDate")
    @SerializedName("release_date")
    val releaseDate: String
) {

    companion object {
        const val TABLE_NAME = "movie_table"
    }

    fun releaseYear(): String{
        return releaseDate.substringBefore("-")
    }
}
