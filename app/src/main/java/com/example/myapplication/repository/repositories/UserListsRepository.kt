package com.example.myapplication.repository.repositories

import com.example.myapplication.models.lists.MovieList
import com.example.myapplication.models.pojo.Film
import com.example.myapplication.models.pojo.TV

interface UserListsRepository {
    suspend fun getFavoriteMoviesList(sessionId: String, page: Int): MovieList.FavouriteMovieList
    suspend fun getFavoriteTVsList(sessionId: String, page: Int): MovieList.FavouriteTVList
    suspend fun getMovieWatchlist(sessionId: String, page: Int): MovieList.MovieWatchList
    suspend fun getTVWatchlist(sessionId: String, page: Int): MovieList.TVWatchList

    sealed class Result {
        sealed class Success : Result() {
            data class FilmSuccess(val films: List<Film>) : Success()
            data class TvSuccess(val persons: List<TV>) : Success()
        }
        object Error : Result()
    }
}