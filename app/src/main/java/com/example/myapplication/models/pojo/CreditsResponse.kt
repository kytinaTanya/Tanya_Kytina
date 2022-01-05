package com.example.myapplication.models.pojo

import com.google.gson.annotations.SerializedName

data class CreditsResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("cast")
    val cast: List<Cast>,

    @SerializedName("crew")
    val crew: List<Crew>
) {
    constructor() : this(
        0,
        emptyList(),
        emptyList()
    )
}