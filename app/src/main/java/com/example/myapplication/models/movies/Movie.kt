package com.example.myapplication.models

import com.google.gson.annotations.SerializedName

sealed class Movie(val mid: Long,
                   val mtitle: String,
                   val mposterPath: String,
                   val mbackdropPath: String)
data class Film(
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
) : Movie(id, title, posterPath, backdropPath) {
    fun releaseYear(): String{
        return releaseDate.substringBefore("-")
    }
}
data class TV(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("backdrop_path")
    val backdropPath: String,

    @SerializedName("vote_average")
    val rating: Float
) : Movie(id, name, posterPath, backdropPath)
