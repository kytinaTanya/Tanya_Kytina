package com.example.myapplication.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myapplication.movies.Movie
import com.example.myapplication.movies.TmdbService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MoviePagingSource @Inject constructor(val service: TmdbService) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = service.getTopRatedMovies(language = "ru-RU", page = nextPageNumber)

            LoadResult.Page(
                data = response.body()?.movies ?: emptyList(),
                prevKey = if(nextPageNumber == 1) null else nextPageNumber.minus(1),
                nextKey = nextPageNumber.plus(1)
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

}