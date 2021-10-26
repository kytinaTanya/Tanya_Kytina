package com.example.myapplication.models.images

import com.google.gson.annotations.SerializedName

data class ImageRequest(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("result")
    val result: UploadeModel
)
