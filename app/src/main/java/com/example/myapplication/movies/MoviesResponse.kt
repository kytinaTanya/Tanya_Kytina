package com.example.myapplication.movies

import com.google.gson.annotations.SerializedName
import retrofit2.Call

data class MoviesResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<Movie>,
    @SerializedName("total_pages") val pages: Int
)
