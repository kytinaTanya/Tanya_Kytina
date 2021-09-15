package com.example.myapplication.models

data class User(
    val uid: String = "",
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val sessionKey: String = ""
)
