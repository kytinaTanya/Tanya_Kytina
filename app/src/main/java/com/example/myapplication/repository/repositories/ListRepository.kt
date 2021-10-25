package com.example.myapplication.repository.repositories

import com.example.myapplication.models.lists.*

interface ListRepository {
    suspend fun getCreatedLists(sessionId: String): List<CreatedList>
    suspend fun getFavoriteMoviesList(sessionId: String): FavouriteMovieList?
    suspend fun getFavoriteTVsList(sessionId: String): FavouriteTVList?
    suspend fun getMovieWatchlist(sessionId: String): MovieWatchList?
    suspend fun getTVWatchlist(sessionId: String): TVWatchList?
}