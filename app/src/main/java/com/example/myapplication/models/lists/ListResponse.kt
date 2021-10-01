package com.example.myapplication.models.lists

import com.example.myapplication.models.movies.TV
import com.google.gson.annotations.SerializedName

data class ListResponse(
    @SerializedName("page")
    val page: Int,

    @SerializedName("result")
    val result: List<CreatedList>,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("total_results")
    val totalResults: Int
)
