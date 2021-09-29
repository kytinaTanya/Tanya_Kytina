package com.example.myapplication.models.movies

import com.example.myapplication.models.Film
import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<Film>,
    @SerializedName("total_pages") val pages: Int
)
