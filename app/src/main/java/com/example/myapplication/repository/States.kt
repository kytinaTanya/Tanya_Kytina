package com.example.myapplication.repository

sealed class States {
    data class Success(val result: Any) : States()
    data class Error(val e: String) : States()
}
