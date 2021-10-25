package com.example.myapplication.models.images

import com.google.gson.annotations.SerializedName

data class BasicImage(
    @SerializedName("id")
    val id: String,

    @SerializedName("server")
    val server: Int,

    @SerializedName("bucket")
    val bucket: Int,

    @SerializedName("filename")
    val filename: String,

    @SerializedName("original_filename")
    val originFilename: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("album_id")
    val albumId: String,

    @SerializedName("album_title")
    val albumTitle: String
)
