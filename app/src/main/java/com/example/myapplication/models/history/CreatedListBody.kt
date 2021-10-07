package com.example.myapplication.models.history

import com.google.gson.annotations.SerializedName

data class CreatedListBody(
    @SerializedName("name")
    val name: String = "History",

    @SerializedName("description")
    val description: String = "This is user history",

    @SerializedName("language")
    val language: String = "ru"
)
