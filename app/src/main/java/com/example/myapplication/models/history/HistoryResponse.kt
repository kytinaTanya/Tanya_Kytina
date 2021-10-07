package com.example.myapplication.models.history

import com.example.myapplication.models.movies.Film
import com.google.gson.annotations.SerializedName

data class HistoryResponse(
    @SerializedName("id")
    val id: String,

    @SerializedName("created_by")
    val createdBy: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("items")
    val items: List<Film>
)
