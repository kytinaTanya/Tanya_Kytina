package com.example.myapplication.models.movies

import com.google.gson.annotations.SerializedName

data class MoviesResponse<T : Movie>(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<T>,
    @SerializedName("total_pages") val pages: Int,
    @SerializedName("total_results") val results: Int
)
