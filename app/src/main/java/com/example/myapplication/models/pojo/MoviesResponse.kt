package com.example.myapplication.models.pojo

import com.google.gson.annotations.SerializedName

data class MoviesResponse<T : BaseItem>(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<T>,
    @SerializedName("total_pages") val pages: Int,
    @SerializedName("total_results") val results: Int
)
