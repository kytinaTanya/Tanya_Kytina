package com.example.myapplication.models.movies

import com.example.myapplication.models.Film
import com.example.myapplication.models.TV
import com.google.gson.annotations.SerializedName

data class TVResponse (
    @SerializedName("page") val page: Int,
    @SerializedName("results") val TV: List<TV>,
    @SerializedName("total_pages") val pages: Int
    )