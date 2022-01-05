package com.example.myapplication.models.pojo

import com.google.gson.annotations.SerializedName

data class MovieNetwork(
    @SerializedName("Name")
    val name: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("logo_path")
    val logo: String,

    @SerializedName("origin_country")
    val country: String
)
