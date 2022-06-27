package com.example.myapplication.models.pojo.view

import com.example.myapplication.models.pojo.BaseItem
import com.example.myapplication.models.pojo.ImageUrlPath

data class PersonView(
    val birthday: String?,
    val deathday: String?,
    val id: Long,
    val name: String?,
    val alsoKnowsAs: List<String>?,
    val gender: Int?,
    val biography: String?,
    val placeOfBirth: String?,
    val profilePath: String?,
    val profilesPhoto: List<ImageUrlPath>?,
    val knownFor: List<BaseItem> = emptyList()
)
