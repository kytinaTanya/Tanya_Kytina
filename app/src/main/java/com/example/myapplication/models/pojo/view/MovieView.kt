package com.example.myapplication.models.pojo.view

import com.example.myapplication.models.pojo.*

data class MovieView(
    val adult: Boolean?,
    val collection: BaseItemDetails.MovieCollection?,
    val budget: Int?,
    val genres: List<MovieGenres>?,
    val homepage: String?,
    val id: Long,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val companies: List<ProductionCompanies>?,
    val countries: List<ProductionCounties>?,
    val releaseDate: String?,
    val revenue: Int?,
    val runtime: Int?,
    val languages: List<SpokenLanguages>?,
    val status: String?,
    val tagline: String?,
    val title: String?,
    val rating: Double?,
    val average: Int?,
    val backdrops: List<ImageUrlPath>?,
    val posters: List<ImageUrlPath>?,
    val cast: List<Cast>?,
    val crew: List<Crew>?,
    val trailers: List<TrailerResult>?,
    val recommendations: List<Film>?,
    val similar: List<Film>?,
    val favorite: Boolean?,
    var myRating: Float?,
    val watchlist: Boolean?
    )
