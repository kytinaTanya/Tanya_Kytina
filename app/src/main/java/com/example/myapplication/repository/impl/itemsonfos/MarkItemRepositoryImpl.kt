package com.example.myapplication.repository.impl.itemsonfos

import com.example.myapplication.firebase.USER
import com.example.myapplication.models.marks.AddToWatchlistMovie
import com.example.myapplication.models.marks.MarkAsFavouriteMovie
import com.example.myapplication.repository.repositories.itemsinfos.MarkItemRepository
import com.example.myapplication.repository.services.TmdbService
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class MarkItemRepositoryImpl(private val service: TmdbService) : MarkItemRepository {
    override suspend fun markAsFavorite(
        id: Long,
        type: String,
        mark: Boolean,
        sessionId: String,
    ): MarkItemRepository.Result {
        return try {
            val response =
                service.markAsFavourite(accountId = USER.tmdbAccountId, sessionId = sessionId,
                    body = MarkAsFavouriteMovie(type, id, mark))
            val result = response.body()
            if (result != null) {
                MarkItemRepository.Result.Success(result)
            } else {
                MarkItemRepository.Result.Error
            }
        } catch (e: HttpException) {
            MarkItemRepository.Result.Error
        } catch (e: ConnectException) {
            MarkItemRepository.Result.Error
        } catch (e: UnknownHostException) {
            MarkItemRepository.Result.Error
        } catch (e: SocketTimeoutException) {
            MarkItemRepository.Result.Error
        }
    }

    override suspend fun addToWatchlist(
        id: Long,
        type: String,
        add: Boolean,
        sessionId: String,
    ): MarkItemRepository.Result {
        return try {
            val response =
                service.addToWatchlist(accountId = USER.tmdbAccountId, sessionId = sessionId,
                    body = AddToWatchlistMovie(type, id, add)
                )
            val result = response.body()
            if (result != null) {
                MarkItemRepository.Result.Success(result)
            } else {
                MarkItemRepository.Result.Error
            }
        } catch (e: HttpException) {
            MarkItemRepository.Result.Error
        } catch (e: ConnectException) {
            MarkItemRepository.Result.Error
        } catch (e: UnknownHostException) {
            MarkItemRepository.Result.Error
        } catch (e: SocketTimeoutException) {
            MarkItemRepository.Result.Error
        }
    }
}