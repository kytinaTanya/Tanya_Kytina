package com.example.myapplication.models.images

import com.google.gson.annotations.SerializedName

data class UploadeModel(
    @SerializedName("max_filesize")
    val max: Int,

    @SerializedName("space_limit")
    val limit: Int,

    @SerializedName("space_used")
    val used: Int,

    @SerializedName("space_left")
    val left: Int,

    @SerializedName("passed")
    val passed: Int,

    @SerializedName("failed")
    val failed: Int,

    @SerializedName("images")
    val images: List<BasicImage>
)
