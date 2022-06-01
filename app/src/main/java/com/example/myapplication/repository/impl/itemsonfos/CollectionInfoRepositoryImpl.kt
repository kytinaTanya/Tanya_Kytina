package com.example.myapplication.repository.impl.itemsonfos

import com.example.myapplication.repository.repositories.itemsinfos.CollectionInfoRepository
import com.example.myapplication.repository.services.TmdbService
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class CollectionInfoRepositoryImpl(private val service: TmdbService) :
    CollectionInfoRepository {

    override suspend fun execute(id: Int): CollectionInfoRepository.Result {
        return try {
            val response = service.getCollectionDetails(id)
            if (response.isSuccessful) {
                if (response.body() != null) {
                    CollectionInfoRepository.Result.Success(response.body()!!)
                } else {
                    CollectionInfoRepository.Result.Error
                }
            } else {
                CollectionInfoRepository.Result.Error
            }
        } catch (e: HttpException) {
            CollectionInfoRepository.Result.Error
        } catch (e: ConnectException) {
            CollectionInfoRepository.Result.Error
        } catch (e: UnknownHostException) {
            CollectionInfoRepository.Result.Error
        } catch (e: SocketTimeoutException) {
            CollectionInfoRepository.Result.Error
        }
    }
}