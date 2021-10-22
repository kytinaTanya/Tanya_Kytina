package com.example.myapplication.models.marks

import com.google.gson.annotations.SerializedName

data class AddToWatchlistMovie(
    @SerializedName("media_type")
    val type: String,

    @SerializedName("media_id")
    val id: Long,

    @SerializedName("watchlist")
    val watchlist: Boolean
)
