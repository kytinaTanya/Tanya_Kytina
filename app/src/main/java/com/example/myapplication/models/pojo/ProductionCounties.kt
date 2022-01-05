package com.example.myapplication.models.pojo

import com.google.gson.annotations.SerializedName

data class ProductionCounties(
    @SerializedName("iso_3166_1")
    val iso: String,

    @SerializedName("name")
    val name: String
)
