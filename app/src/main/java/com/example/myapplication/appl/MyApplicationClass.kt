package com.example.myapplication.appl

import android.app.Application
import com.example.myapplication.di.AppComponent
import com.example.myapplication.di.AppModule
import com.example.myapplication.di.DaggerAppComponent
import com.example.myapplication.listeners.ActivityLifecycleListener
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplicationClass : Application() {

    companion object{
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(ActivityLifecycleListener())
        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}