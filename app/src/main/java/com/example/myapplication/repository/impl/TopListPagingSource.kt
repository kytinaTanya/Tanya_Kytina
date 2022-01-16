package com.example.myapplication.repository.impl

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myapplication.models.pojo.BaseItem
import com.example.myapplication.models.pojo.MoviesResponse
import com.example.myapplication.repository.services.TmdbService

class TopListPagingSource(val service: TmdbService) : PagingSource<Int, MoviesResponse<BaseItem>>() {
    override fun getRefreshKey(state: PagingState<Int, MoviesResponse<BaseItem>>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesResponse<BaseItem>> {
        TODO("Not yet implemented")
    }
}
