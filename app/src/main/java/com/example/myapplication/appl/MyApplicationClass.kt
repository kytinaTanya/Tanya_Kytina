package com.example.myapplication.appl

import android.app.Application
import com.example.myapplication.listeners.ActivityLifecycleListener
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(ActivityLifecycleListener())
    }
}