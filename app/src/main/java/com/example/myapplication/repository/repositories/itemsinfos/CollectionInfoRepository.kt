package com.example.myapplication.repository.repositories.itemsinfos

import com.example.myapplication.models.pojo.BaseItemDetails

interface CollectionInfoRepository {
    suspend fun execute(id: Int): Result

    sealed class Result {
        data class Success(val data: BaseItemDetails.MovieCollection) : Result()
        object Error : Result()
    }
}