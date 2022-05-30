package com.example.myapplication.models.lists

import com.example.myapplication.models.pojo.BaseItem
import com.example.myapplication.models.pojo.Film
import com.example.myapplication.models.pojo.TV
import com.google.gson.annotations.SerializedName

sealed class MovieList {
    abstract val page: Int
    abstract val list: List<BaseItem>
    abstract val totalPages: Int
    abstract val totalResults: Int

    data class FavouriteMovieList(
        @SerializedName("page")
        override val page: Int,

        @SerializedName("results")
        override val list: List<Film>,

        @SerializedName("total_pages")
        override val totalPages: Int,

        @SerializedName("total_results")
        override val totalResults: Int
    ): MovieList()

    data class MovieWatchList(
        @SerializedName("page")
        override val page: Int,

        @SerializedName("results")
        override val list: List<Film>,

        @SerializedName("total_pages")
        override val totalPages: Int,

        @SerializedName("total_results")
        override val totalResults: Int
    ): MovieList()

    data class FavouriteTVList(
        @SerializedName("page")
        override val page: Int,

        @SerializedName("results")
        override val list: List<TV>,

        @SerializedName("total_pages")
        override val totalPages: Int,

        @SerializedName("total_results")
        override val totalResults: Int
    ): MovieList()

    data class TVWatchList(
        @SerializedName("page")
        override val page: Int,

        @SerializedName("results")
        override val list: List<TV>,

        @SerializedName("total_pages")
        override val totalPages: Int,

        @SerializedName("total_results")
        override val totalResults: Int
    ): MovieList()
}

