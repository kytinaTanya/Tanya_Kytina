package com.example.myapplication.models

import com.google.gson.annotations.SerializedName

data class RetrofitPostToken(
    @SerializedName("request_token")
    val requestToken: String
)
