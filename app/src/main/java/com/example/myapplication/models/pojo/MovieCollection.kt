package com.example.myapplication.models.pojo

import com.google.gson.annotations.SerializedName

data class MovieCollection(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("backdrop_path")
    val backdrop: String,

    @SerializedName("parts")
    val parts: List<Film>
): BaseItem()
