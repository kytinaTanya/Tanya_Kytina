package com.example.myapplication.repository.repositories.itemsinfos

import com.example.myapplication.models.pojo.view.PersonView

interface PersonInfoRepository {
    suspend fun execute(id: Long): Result

    sealed class Result {
        data class Success(val data: PersonView) : Result()
        object Error : Result()
    }
}