package com.example.myapplication.models

import com.google.gson.annotations.SerializedName

data class RequestToken(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("expires_at")
    val expires: String,

    @SerializedName("request_token")
    val requestToken: String
)
