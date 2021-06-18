package com.example.myapplication

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class LifecycleListener : LifecycleObserver {
    fun registerLifecycle(lifecycle : Lifecycle){
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun create(){
        Log.d("OnCreate","Activity is ON_CREATE")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start(){
        Log.d("OnStart","Activity is ON_START")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resume() {
        Log.d("OnResume","Activity is ON_RESUME")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause() {
        Log.d("onPause","Activity is ON_PAUSE")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop(){
        Log.d("OnStop","Activity is ON_STOP")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy(){
        Log.d("OnDestroy","Activity is ON_DESTROY")
    }
}