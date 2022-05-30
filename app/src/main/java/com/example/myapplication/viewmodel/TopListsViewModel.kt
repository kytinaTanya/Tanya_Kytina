package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.myapplication.repository.pagingsources.TopListPagingSource
import com.example.myapplication.repository.services.TmdbService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopListsViewModel @Inject constructor(val service: TmdbService) : ViewModel() {
    fun getData(request: MainScreenRequest) = Pager(
        PagingConfig(20, 5)
    ) {
        TopListPagingSource(service, request)
    }.flow
}
