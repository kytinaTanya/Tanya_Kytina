package com.example.myapplication.repository.services

import com.example.myapplication.BuildConfig
import com.example.myapplication.models.images.ImageRequest
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ImageService {
    companion object {
        fun createImageService(): ImageService {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(BuildConfig.API_IMAGE_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ImageService::class.java)
        }
    }

    @Multipart
    @POST("images")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
        @Query("api_key") key: String = BuildConfig.IMAGE_API_KEY
    ): Call<ImageRequest>
}