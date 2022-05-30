package com.example.myapplication.states

import com.example.myapplication.models.pojo.Episode
import com.example.myapplication.models.pojo.Film
import com.example.myapplication.models.pojo.TV

sealed class SearchScreenViewState : ViewState {
    sealed class Success : SearchScreenViewState() {
        data class FilmSuccess(val films: List<Film>) : Success()
        data class TvSuccess(val tvs: List<TV>) : Success()
        data class EpisodeSuccess(val episodes: List<Episode>) : Success()
    }
    object Error : SearchScreenViewState()
    object Loading : SearchScreenViewState()
}