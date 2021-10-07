package com.example.myapplication.models.lists

import com.example.myapplication.models.movies.Film
import com.example.myapplication.models.movies.TV
import com.google.gson.annotations.SerializedName

sealed class MovieList

data class CreatedList(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("item_count")
    val itemCount: Int,

    @SerializedName("description")
    val description: String,

    @SerializedName("list_type")
    val listType: String
): MovieList()

data class FavouriteMovieList(
    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val result: List<Film>,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("total_results")
    val totalResults: Int
): MovieList()

data class MovieWatchList(
    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val result: List<Film>,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("total_results")
    val totalResults: Int
): MovieList()

data class FavouriteTVList(
    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val result: List<TV>,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("total_results")
    val totalResults: Int
): MovieList()

data class TVWatchList(
    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val result: List<TV>,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("total_results")
    val totalResults: Int
): MovieList()

