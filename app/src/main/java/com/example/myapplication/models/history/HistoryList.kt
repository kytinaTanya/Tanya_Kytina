package com.example.myapplication.models.history

import com.google.gson.annotations.SerializedName

data class HistoryList(
    @SerializedName("status_message")
    val message: String,

    @SerializedName("success")
    val success: Boolean,

    @SerializedName("status_code")
    val status: Int,

    @SerializedName("list_id")
    val id: Int
)