package com.example.myapplication.states

import com.example.myapplication.models.pojo.Film
import com.example.myapplication.models.pojo.Person
import com.example.myapplication.models.pojo.TV

sealed class MainViewState : ViewState {
    sealed class Success : MainViewState() {
        data class FilmSuccess(val list: List<Film>) : Success()
        data class TvSuccess(val list: List<TV>) : Success()
        data class PersonSuccess(val list: List<Person>) : Success()
    }
    object Error : MainViewState()
    object Loading : MainViewState()
}
