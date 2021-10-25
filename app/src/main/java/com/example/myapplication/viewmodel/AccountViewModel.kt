package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.repositories.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {
    fun uploadImage(inputStream: InputStream) {
        val part: MultipartBody.Part = MultipartBody.Part.createFormData(
            "pic", "myPic", RequestBody.create(
                "image/*".toMediaTypeOrNull(),
                inputStream.readBytes()
            )
        )
        viewModelScope.launch {
            accountRepository.uploadImage(part)
        }
    }
}