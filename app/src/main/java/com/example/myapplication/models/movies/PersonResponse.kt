package com.example.myapplication.models.movies

import com.google.gson.annotations.SerializedName

data class PersonResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val persons: List<Person>,
    @SerializedName("total_pages") val pages: Int
)
