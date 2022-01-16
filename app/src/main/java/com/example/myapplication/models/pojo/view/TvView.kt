package com.example.myapplication.models.pojo.view

import com.example.myapplication.models.pojo.*

data class TvView (
    val createdBy: List<TvProducer>,
    val runtime: List<Int>,
    val firstAirDate: String,
    val genres: List<MovieGenres>,
    val homepage: String,
    val id: Long,
    val inProduction: Boolean,
    val languages: List<String>,
    val lastAirDate: String,
    val lastEpisode: Episode,
    val name: String,
    val networks: List<MovieNetwork>,
    val episodes: Int,
    val numOfSeasons: Int,
    val country: List<String>,
    val originalLanguage: String,
    val originalName: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val companies: List<ProductionCompanies>,
    val countries: List<ProductionCounties>,
    var seasons: List<Season>,
    val spokenLanguages: List<SpokenLanguages>,
    val status: String,
    val tagline: String,
    val type: String,
    val rating: Double,
    val average: Int,
    val backdrops: List<ImageUrlPath>,
    val posters: List<ImageUrlPath>,
    val cast: List<Cast>,
    val crew: List<Crew>,
    val videos: List<TrailerResult>,
    val recommendations: List<TV>,
    val similar: List<TV>,
    val favorite: Boolean,
    var myRating: Float,
    val watchlist: Boolean
    )