package com.example.myapplication

import android.app.Application
import com.example.myapplication.listeners.ActivityLifecycleListener

class MyApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()
        ActivityLifecycleListener().init(this)
    }
}