package com.example.myapplication.repository.impl

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myapplication.models.pojo.BaseItem
import com.example.myapplication.repository.services.TmdbService
import com.example.myapplication.viewmodel.MainScreenRequest
import retrofit2.HttpException
import java.io.IOException

class TopListPagingSource (val service: TmdbService, private val request: MainScreenRequest) : PagingSource<Int, BaseItem>() {
    override fun getRefreshKey(state: PagingState<Int, BaseItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BaseItem> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = when (request) {
                MainScreenRequest.TOP_RATED_MOVIES -> service.getTopRatedMovies(page = nextPageNumber)
                MainScreenRequest.POPULAR_MOVIES -> service.getPopularMovies(page = nextPageNumber)
                MainScreenRequest.NOW_PLAYING_MOVIES -> service.getNowPlayingMovies(page = nextPageNumber)
                MainScreenRequest.UPCOMING_MOVIES -> service.getUpcomingMovies(page = nextPageNumber)
                MainScreenRequest.TOP_RATED_TVS -> service.getTopRatedTV(page = nextPageNumber)
                MainScreenRequest.AIRING_TODAY_TVS -> service.getOnAirTodayTV(page = nextPageNumber)
                MainScreenRequest.ON_THE_AIR_TVS -> service.getNowOnAirTV(page = nextPageNumber)
                MainScreenRequest.POPULAR_TVS -> service.getPopularTV(page = nextPageNumber)
                MainScreenRequest.POPULAR_PERSONS -> service.getPopularPersons(page = nextPageNumber)
            }
            if (response.isSuccessful && response.body() != null) {
                LoadResult.Page(
                    data = response.body()!!.items,
                    prevKey = if(nextPageNumber == 1) null else nextPageNumber.minus(1),
                    nextKey = if (nextPageNumber < response.body()!!.pages) nextPageNumber.plus(1) else null
                )
            } else {
                LoadResult.Error(EmptyResponseException())
            }

        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}

class EmptyResponseException : Exception()