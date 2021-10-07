package com.example.myapplication.models.history

import com.google.gson.annotations.SerializedName

data class MediaBody(
    @SerializedName("media_id")
    val id: Long
)
