package com.example.myapplication.di

import com.example.myapplication.ui.SecondFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
    AppModule::class,
    ViewModelModule::class,
    PagingSourceModule::class,
    TmdbServiceModule::class,
    RepositoryDbModule::class,
    MovieDbModule::class))

interface AppComponent {
    //Fragment
    fun inject(fragment: SecondFragment)
}