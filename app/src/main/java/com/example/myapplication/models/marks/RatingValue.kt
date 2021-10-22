package com.example.myapplication.models.marks

import com.google.gson.annotations.SerializedName

data class RatingValue (
    @SerializedName("value")
    val rating: Float = 0.0F
)
