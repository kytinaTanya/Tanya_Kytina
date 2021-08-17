package com.example.myapplication.di

import com.example.myapplication.ui.MovieFragment
import com.example.myapplication.ui.SecondFragment
import dagger.Component

@Component(modules = arrayOf(ViewModelModule::class, RepositoryModule::class, TmdbServiceModule::class))

interface AppComponent {
    //Fragment
    fun inject(fragment: SecondFragment)
    fun inject(fragment: MovieFragment)
}