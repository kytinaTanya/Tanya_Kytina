package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.movies.Film
import com.example.myapplication.repository.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val historyRepository: HistoryRepository) : ViewModel() {
    private var _films: MutableLiveData<List<Film>> = MutableLiveData()
    val films: LiveData<List<Film>>
        get() = _films

    fun loadHistory(id: Int, sessionId: String) {
        viewModelScope.launch {
            _films.value = historyRepository.getDetailsAboutHistoryList(id, sessionId)
        }
    }
}