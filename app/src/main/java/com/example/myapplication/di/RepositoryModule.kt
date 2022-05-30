package com.example.myapplication.di

import com.example.myapplication.repository.impl.AccountRepositoryImpl
import com.example.myapplication.repository.impl.DetailsRepositoryImpl
import com.example.myapplication.repository.impl.RepositoryImpl
import com.example.myapplication.repository.impl.UserListsRepositoryImpl
import com.example.myapplication.repository.impl.search_screen.RatedItemsRepositoryImpl
import com.example.myapplication.repository.impl.search_screen.SearchRepositoryImpl
import com.example.myapplication.repository.repositories.*
import com.example.myapplication.repository.repositories.search_screen.RatedItemsRepository
import com.example.myapplication.repository.repositories.search_screen.SearchRepository
import com.example.myapplication.repository.services.ImageService
import com.example.myapplication.repository.services.TmdbService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(service: TmdbService) : Repository {
        return RepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(service: TmdbService) : AuthRepository {
        return RepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun provideUserListsRepository(service: TmdbService) : UserListsRepository {
        return UserListsRepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun provideDetailsRepository(service: TmdbService) : DetailsRepository {
        return DetailsRepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun provideHistoryRepository(service: TmdbService) : SearchRepository {
        return SearchRepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun provideRatedItemsRepository(service: TmdbService) : RatedItemsRepository {
        return RatedItemsRepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun provideAccountRepository(imageService: ImageService) : AccountRepository {
        return AccountRepositoryImpl(imageService)
    }
}