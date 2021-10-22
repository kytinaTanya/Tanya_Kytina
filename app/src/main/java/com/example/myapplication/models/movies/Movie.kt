package com.example.myapplication.models.movies

import com.google.gson.annotations.SerializedName

sealed class Movie()

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
) : Movie() {
    constructor() : this(
        0,
        "Non",
        "Non",
        "Non",
        "Non",
        0.0F,
        "Non"
    )

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
) : Movie() {
    constructor() : this(
        0,
        "Non",
        "Non",
        "Non",
        "Non",
        0.0F
    )
}

data class Episode(
    @SerializedName("id")
    val id: Long,

    @SerializedName("episode_number")
    val episodeNum: Int,

    @SerializedName("season_number")
    val seasonNum: Int,

    @SerializedName("show_id")
    val showId: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("still_path")
    val stillPath: String,

    @SerializedName("vote_average")
    val rating: Double
) : Movie() {
    constructor() : this(
        0,
        0,
        0,
        0,
        "Non",
        "Non",
        "Non",
        0.0
    )
}

data class Person(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("profile_path")
    val profilePath: String,

    @SerializedName("adult")
    val adult: Boolean,

    @SerializedName("biography")
    val biography: String,

    @SerializedName("popularity")
    val popularity: Number
): Movie() {
    constructor() : this(
        0,
        "Non",
        "Non",
        false,
        "Non",
        0
    )
}
