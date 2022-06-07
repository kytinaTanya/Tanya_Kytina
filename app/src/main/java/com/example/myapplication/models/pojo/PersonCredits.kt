package com.example.myapplication.models.pojo

import com.google.gson.annotations.SerializedName

data class PersonCredits<T>(
    @SerializedName("cast")
    val cast: List<T>,

    @SerializedName("crew")
    val crew: List<T>
)
