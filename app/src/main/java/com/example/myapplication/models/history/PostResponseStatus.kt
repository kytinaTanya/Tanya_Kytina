package com.example.myapplication.models.history

import com.google.gson.annotations.SerializedName

data class PostResponseStatus(
    @SerializedName("status_code")
    val status: Int = 0,

    @SerializedName("status_message")
    val message: String = ""
)