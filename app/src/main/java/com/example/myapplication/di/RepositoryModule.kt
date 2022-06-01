package com.example.myapplication.di

import com.example.myapplication.repository.impl.AccountRepositoryImpl
import com.example.myapplication.repository.impl.RepositoryImpl
import com.example.myapplication.repository.impl.UserListsRepositoryImpl
import com.example.myapplication.repository.impl.itemsonfos.*
import com.example.myapplication.repository.impl.itemsonfos.rate.RateEpisodeRepositoryImpl
import com.example.myapplication.repository.impl.itemsonfos.rate.RateMovieRepositoryImpl
import com.example.myapplication.repository.impl.itemsonfos.rate.RateTvRepositoryImpl
import com.example.myapplication.repository.impl.search_screen.RatedItemsRepositoryImpl
import com.example.myapplication.repository.impl.search_screen.SearchRepositoryImpl
import com.example.myapplication.repository.repositories.AccountRepository
import com.example.myapplication.repository.repositories.AuthRepository
import com.example.myapplication.repository.repositories.Repository
import com.example.myapplication.repository.repositories.UserListsRepository
import com.example.myapplication.repository.repositories.itemsinfos.*
import com.example.myapplication.repository.repositories.itemsinfos.rate.RateEpisodeRepository
import com.example.myapplication.repository.repositories.itemsinfos.rate.RateTvRepository
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
    fun provideDetailsRepository(service: TmdbService) : MovieInfoRepository {
        return MovieInfoRepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun providePersonInfoRepository(service: TmdbService) : PersonInfoRepository {
        return PersonInfoRepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun provideTvInfoRepository(service: TmdbService) : TvInfoRepository {
        return TvInfoRepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun provideCollectionInfoRepository(service: TmdbService) : CollectionInfoRepository {
        return CollectionInfoRepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun provideSeasonInfoRepository(service: TmdbService) : SeasonInfoRepository {
        return SeasonInfoRepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun provideEpisodeInfoRepository(service: TmdbService) : EpisodeInfoRepository {
        return EpisodeInfoRepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun provideRateEpisodeRepository(service: TmdbService) : RateEpisodeRepository {
        return RateEpisodeRepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun provideRateMovieRepository(service: TmdbService) : RateMovieRepository {
        return RateMovieRepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun provideRateTvRepository(service: TmdbService) : RateTvRepository {
        return RateTvRepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun provideMarkItemRepository(service: TmdbService) : MarkItemRepository {
        return MarkItemRepositoryImpl(service)
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