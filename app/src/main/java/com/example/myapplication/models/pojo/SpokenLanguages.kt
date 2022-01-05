package com.example.myapplication.models.pojo

import com.google.gson.annotations.SerializedName

data class SpokenLanguages(
    @SerializedName("english_name")
    val englishName: String = "",

    @SerializedName("iso_639_1")
    val iso: String,

    @SerializedName("name")
    val name: String
)