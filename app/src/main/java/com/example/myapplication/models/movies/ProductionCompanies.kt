package com.example.myapplication.models.movies

import com.google.gson.annotations.SerializedName

data class ProductionCompanies(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("logo_path")
    val logo: String,

    @SerializedName("origin_country")
    val country: String
): BaseItem()