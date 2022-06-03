package com.example.myapplication.repository.impl

import com.example.myapplication.repository.repositories.AccountRepository
import com.example.myapplication.repository.services.ImageService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class AccountRepositoryImpl(private val imageService: ImageService) : AccountRepository {

    override suspend fun uploadImage(
        inputStream: InputStream
    ): AccountRepository.Result {
        return try {
            val part: MultipartBody.Part = MultipartBody.Part.createFormData(
                "pic", "myPic", inputStream.readBytes()
                    .toRequestBody(
                        "image/*".toMediaTypeOrNull(),
                        0
                    )
            )
            val response = imageService.uploadImage(file = part)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                AccountRepository.Result.Success(
                    body.result.images[0].link.replace("\\", "")
                )
            } else {
                AccountRepository.Result.Error
            }
        } catch (e: ConnectException) {
            AccountRepository.Result.Error
        } catch (e: UnknownHostException) {
            AccountRepository.Result.Error
        } catch (e: SocketTimeoutException) {
            AccountRepository.Result.Error
        }
    }
}