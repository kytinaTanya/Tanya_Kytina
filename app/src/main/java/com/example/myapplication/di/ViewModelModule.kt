package com.example.myapplication.di

import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.repository.Repository
import com.example.myapplication.viewmodel.MovieViewModel
import com.example.myapplication.viewmodel.MovieViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindsViewModelFactory(factory: MovieViewModelFactory): ViewModelProvider.Factory
}