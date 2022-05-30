package com.example.myapplication.repository.impl

import com.example.myapplication.models.lists.MovieList
import com.example.myapplication.repository.repositories.UserListsRepository
import com.example.myapplication.repository.services.TmdbService
import retrofit2.HttpException
import javax.inject.Inject

class UserListsRepositoryImpl @Inject constructor(private val service: TmdbService) : UserListsRepository {
    override suspend fun getFavoriteMoviesList(sessionId: String, page: Int): MovieList.FavouriteMovieList {
        val response = service.getFavouriteMovieList(sessionId = sessionId, page = page)
        return if (response.isSuccessful) {
            val responseBody = response.body() ?: throw (HttpException(response))
            responseBody
        } else {
            throw (HttpException(response))
        }
    }

    override suspend fun getFavoriteTVsList(sessionId: String, page: Int): MovieList.FavouriteTVList {
        val response = service.getFavouriteTVList(sessionId = sessionId, page = page)
        return if (response.isSuccessful) {
            val responseBody = response.body() ?: throw (HttpException(response))
            responseBody
        } else {
            throw(HttpException(response))
        }
    }

    override suspend fun getMovieWatchlist(sessionId: String, page: Int): MovieList.MovieWatchList {
        val response = service.getMovieWatchlist(sessionId = sessionId, page = page)
        return if (response.isSuccessful) {
            val responseBody = response.body() ?: throw (HttpException(response))
            responseBody
        } else {
            throw(HttpException(response))
        }
    }

    override suspend fun getTVWatchlist(sessionId: String, page: Int): MovieList.TVWatchList {
        val response = service.getTVWatchlist(sessionId = sessionId, page = page)
        return if (response.isSuccessful) {
            val responseBody = response.body() ?: throw (HttpException(response))
            responseBody
        } else {
            throw(HttpException(response))
        }
    }
}