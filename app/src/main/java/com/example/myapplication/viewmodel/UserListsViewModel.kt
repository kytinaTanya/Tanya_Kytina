package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.myapplication.repository.UserListsType
import com.example.myapplication.repository.pagingsources.UserListsPagingSource
import com.example.myapplication.repository.repositories.UserListsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserListsViewModel @Inject constructor(
    private val userListsRepository: UserListsRepository,
) : ViewModel() {

    val tag: String = UserListsViewModel::class.java.simpleName

    fun loadFavoriteMoviesList(sessionId: String) = Pager(
        PagingConfig(20, 5)
    ) {
        UserListsPagingSource(userListsRepository, sessionId, UserListsType.FavoriteMovies)
    }.flow

    fun getFavoriteTVsList(sessionId: String) = Pager(
        PagingConfig(20, 5)
    ) {
        UserListsPagingSource(userListsRepository, sessionId, UserListsType.FavoriteTvs)
    }.flow

    fun loadMovieWatchlist(sessionId: String) = Pager(
        PagingConfig(20, 5)
    ) {
        UserListsPagingSource(userListsRepository, sessionId, UserListsType.MovieWatchlist)
    }.flow

    fun loadTVWatchlist(sessionId: String) = Pager(
        PagingConfig(20, 5)
    ) {
        UserListsPagingSource(userListsRepository, sessionId, UserListsType.TvWatchlist)
    }.flow
}