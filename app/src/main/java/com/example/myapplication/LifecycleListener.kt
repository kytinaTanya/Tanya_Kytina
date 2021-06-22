package com.example.myapplication

import android.app.Activity
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class LifecycleListener(private val activity: Activity) : LifecycleObserver {

    fun registerLifecycle(lifecycle : Lifecycle){
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun create(){
        Log.d("OnCreate","${activity.javaClass.simpleName} is ON_CREATE")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start(){
        Log.d("OnStart","${activity.javaClass.simpleName} is ON_START")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resume() {
        Log.d("OnResume","${activity.javaClass.simpleName} is ON_RESUME")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause() {
        Log.d("OnPause","${activity.javaClass.simpleName} is ON_PAUSE")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop(){
        Log.d("OnStop","${activity.javaClass.simpleName} is ON_STOP")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy(){
        Log.d("OnDestroy","${activity.javaClass.simpleName} is ON_DESTROY")
    }
}