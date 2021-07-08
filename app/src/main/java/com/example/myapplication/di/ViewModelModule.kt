package com.example.myapplication.di

import com.example.myapplication.repository.Repository
import com.example.myapplication.viewmodel.MovieViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {
    @Provides
    fun provideMovieViewModel(repository: Repository) : MovieViewModel {
        return MovieViewModel(repository)
    }
}