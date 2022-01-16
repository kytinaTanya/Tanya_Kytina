package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.repository.impl.TopListPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class TopListsViewModel(val pagingSource: TopListPagingSource) : ViewModel() {

}
