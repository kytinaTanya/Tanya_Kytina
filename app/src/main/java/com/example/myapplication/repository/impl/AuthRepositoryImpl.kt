package com.example.myapplication.repository.impl

import android.util.Log
import com.example.myapplication.models.RetrofitPostToken
import com.example.myapplication.repository.repositories.AuthRepository
import com.example.myapplication.repository.services.TmdbService
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val service: TmdbService) : AuthRepository {
    override suspend fun getRequestToken(): AuthRepository.StringResult {
        return try {
            val response = service.getRequestToken()
            if (response.isSuccessful) {
                val req = response.body()?.requestToken ?: RepositoryImpl.EMPTY_STRING
                Log.d("REQUESTTOKEN", req)
                AuthRepository.StringResult.Success(req)
            } else {
                AuthRepository.StringResult.Error
            }
        } catch (e: ConnectException) {
            AuthRepository.StringResult.Error
        } catch (e: UnknownHostException) {
            AuthRepository.StringResult.Error
        } catch (e: SocketTimeoutException) {
            AuthRepository.StringResult.Error
        }
    }

    override suspend fun createSessionId(requestToken: String): AuthRepository.StringResult {
        return try {
            if (requestToken == RepositoryImpl.EMPTY_STRING) {
                Log.d("TOKEN", "Not found")
                AuthRepository.StringResult.Error
            }
            val body = RetrofitPostToken(requestToken)
            val response = service.postSession(body = body)
            Log.d("POSTRESPONSE", response.message())
            if (response.isSuccessful && response.body()?.sessionId != null) {
                AuthRepository.StringResult.Success(response.body()!!.sessionId)
            } else {
                AuthRepository.StringResult.Error
            }
        } catch (e: ConnectException) {
            AuthRepository.StringResult.Error
        } catch (e: UnknownHostException) {
            AuthRepository.StringResult.Error
        } catch (e: SocketTimeoutException) {
            AuthRepository.StringResult.Error
        }
    }

    override suspend fun getAccountInfo(sessionId: String): AuthRepository.IntResult {
        return try {
            val response = service.getAccountDetails(sessionId = sessionId)
            if (response.isSuccessful && response.body() != null) {
                AuthRepository.IntResult.Success(response.body()!!.id)
            } else {
                AuthRepository.IntResult.Error
            }
        } catch (e: ConnectException) {
            AuthRepository.IntResult.Error
        } catch (e: UnknownHostException) {
            AuthRepository.IntResult.Error
        } catch (e: SocketTimeoutException) {
            AuthRepository.IntResult.Error
        }
    }
}