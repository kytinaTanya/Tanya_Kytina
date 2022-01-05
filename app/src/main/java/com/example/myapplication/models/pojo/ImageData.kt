package com.example.myapplication.models.pojo

import com.google.gson.annotations.SerializedName

data class ImageData(
    @SerializedName("id")
    val id: Long,

    @SerializedName("backdrops")
    val backdrops: List<ImageUrlPath>,

    @SerializedName("posters")
    val posters: List<ImageUrlPath>,

    @SerializedName("profiles")
    val profiles: List<ImageUrlPath>,

    @SerializedName("stills")
    val stills: List<ImageUrlPath>,

    @SerializedName("logos")
    val logos: List<LogoPath>
) {
    constructor() : this(
        0L,
        emptyList(),
        emptyList(),
        emptyList(),
        emptyList(),
        emptyList()
    )
}

data class LogoPath(
    @SerializedName("aspect_ratio")
    val aspectRatio: Number,

    @SerializedName("file_path")
    val path: String,

    @SerializedName("height")
    val height: Int,

    @SerializedName("id")
    val id: String,

    @SerializedName("file_type")
    val type: String,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("vote_count")
    val voteCount: Int,

    @SerializedName("width")
    val width: Int
)

data class ImageUrlPath(
    @SerializedName("aspect_ratio")
    val aspectRatio: Number,

    @SerializedName("file_path")
    val path: String,

    @SerializedName("height")
    val height: Int,

    @SerializedName("iso_639_1")
    val iso: String,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("vote_count")
    val voteCount: Int,

    @SerializedName("width")
    val width: Int
)
