package com.example.myapplication.models.movies

import com.google.gson.annotations.SerializedName

sealed class Movie(val mid: Long)

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
) : Movie(id) {
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
) : Movie(id)

data class Person(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("profile_path")
    val profilePath: String,

    @SerializedName("adult")
    val adult: Boolean,

    @SerializedName("popularity")
    val popularity: Number
): Movie(id)
