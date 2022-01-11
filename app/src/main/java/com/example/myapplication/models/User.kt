package com.example.myapplication.models

data class User(
    val id: String = "",
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val sessionKey: String = "",
    val accessToken: String = "",
    val historyListID: Int = 0,
    val profileUrl: String = "https://library.kissclipart.com/20180918/rse/kissclipart-avatar-blue-icon-clipart-computer-icons-avatar-cli-a6b01992f1cd42fe.png"
)
