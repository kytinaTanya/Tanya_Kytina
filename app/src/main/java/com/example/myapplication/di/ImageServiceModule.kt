package com.example.myapplication.di

import com.example.myapplication.repository.services.ImageService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ImageServiceModule {
    @Provides
    @Singleton
    fun provideImageService() : ImageService {
        return ImageService.createImageService()
    }
}