package com.example.myapplication.models

import com.google.gson.annotations.SerializedName

data class SessionId(
        @SerializedName("success")
        val success: Boolean,

        @SerializedName("session_id")
        val sessionId: String
)
