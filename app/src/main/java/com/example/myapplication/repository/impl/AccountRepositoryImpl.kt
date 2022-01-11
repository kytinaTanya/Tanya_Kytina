package com.example.myapplication.repository.impl

import android.util.Log
import com.example.myapplication.models.images.ImageRequest
import com.example.myapplication.repository.repositories.AccountRepository
import com.example.myapplication.repository.services.ImageService
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response

class AccountRepositoryImpl(private val imageService: ImageService): AccountRepository {

    override suspend fun uploadImage(file: MultipartBody.Part, onSuccess: (String) -> Unit){
        imageService.uploadImage(file = file)
            .enqueue(object: retrofit2.Callback<ImageRequest> {
                override fun onResponse(
                    call: Call<ImageRequest>,
                    response: Response<ImageRequest>
                ) {
                    if(response.isSuccessful) {
                        val responseBody = response.body()
                        if(responseBody != null) {
                            onSuccess(responseBody.result.images[0].link.replace("\\", ""))
                            Log.d("Success", "Image id: ${responseBody.result.images[0].id}")
                        } else {
                            onSuccess("")
                        }
                    }
                }

                override fun onFailure(call: Call<ImageRequest>, t: Throwable) {
                    Log.d("Failed", "$t")
                }

            })
    }
}