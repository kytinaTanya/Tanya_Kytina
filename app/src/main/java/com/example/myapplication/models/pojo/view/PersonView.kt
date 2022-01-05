package com.example.myapplication.models.pojo.view

import com.example.myapplication.models.pojo.ImageUrlPath

data class PersonView(
    val birthday: String,
    val knownFor: String,
    val deathday: String?,
    val id: Long,
    val name: String,
    val alsoKnowsAs: List<String>,
    val gender: Int,
    val biography: String,
    val popularity: Number,
    val placeOfBirth: String,
    val profilePath: String,
    val adult: Boolean,
    val imdbId: String,
    val homepage: String?,
    val profilesPhoto: List<ImageUrlPath>
)
