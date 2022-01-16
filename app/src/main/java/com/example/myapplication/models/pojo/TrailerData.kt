package com.example.myapplication.models.pojo

import com.google.gson.annotations.SerializedName

data class TrailerData(
    @SerializedName("id")
    val id: Long,

    @SerializedName("results")
    val results: List<TrailerResult>
)

data class TrailerResult(
    @SerializedName("name")
    val name: String,

    @SerializedName("key")
    val key: String,

    @SerializedName("site")
    val site: String,

    @SerializedName("type")
    val type: String
)
