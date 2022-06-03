package com.example.myapplication.models

import com.example.myapplication.BuildConfig
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    val id: String = "",
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val sessionKey: String = "",
    val tmdbAccountId: Int = 0,
    val profileUrl: String = BuildConfig.DEFAULT_ACCOUNT_IMAGE_URL
)
