package com.example.myapplication.repository.pagingsources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myapplication.models.pojo.BaseItem
import com.example.myapplication.repository.UserListsType
import com.example.myapplication.repository.repositories.UserListsRepository
import retrofit2.HttpException
import java.io.IOException

class UserListsPagingSource(
    private val userListsRepository: UserListsRepository,
    private val sessionId: String,
    private val type: UserListsType
) : PagingSource<Int, BaseItem>() {
    override fun getRefreshKey(state: PagingState<Int, BaseItem>): Int? =
        state.anchorPosition?.let { position ->
            val anchorPage = state.closestPageToPosition(position)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BaseItem> {
        return try {
            val nextPage = params.key ?: 1
            val result = when (type) {
                UserListsType.FavoriteMovies ->
                    userListsRepository.getFavoriteMoviesList(sessionId, nextPage)
                UserListsType.FavoriteTvs ->
                    userListsRepository.getFavoriteTVsList(sessionId, nextPage)
                UserListsType.MovieWatchlist ->
                    userListsRepository.getMovieWatchlist(sessionId, nextPage)
                UserListsType.TvWatchlist ->
                    userListsRepository.getTVWatchlist(sessionId, nextPage)
            }
            LoadResult.Page(
                data = result.list,
                prevKey = if (nextPage == 1) null else nextPage.minus(1),
                nextKey = if (nextPage < result.totalPages) nextPage.plus(1) else null
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}
