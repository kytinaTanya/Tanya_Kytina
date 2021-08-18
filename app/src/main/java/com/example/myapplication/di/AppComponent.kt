package com.example.myapplication.di

import com.example.myapplication.ui.MovieFragment
import com.example.myapplication.ui.SecondFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelModule::class,
    RepositoryModule::class,
    TmdbServiceModule::class,
    AppModule::class,
    RoomModule::class])

interface AppComponent {
    //Fragment
    fun inject(fragment: SecondFragment)
    fun inject(fragment: MovieFragment)
}