package com.example.myapplication

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity

class MyApplicationClass : Application(), Application.ActivityLifecycleCallbacks {
    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Log.d("OnCreate","${activity.javaClass.simpleName} is ON_CREATE")
    }

    override fun onActivityStarted(activity: Activity) {
        Log.d("OnStart","${activity.javaClass.simpleName} is ON_START")
    }

    override fun onActivityResumed(activity: Activity) {
        Log.d("OnResume","${activity.javaClass.simpleName} is ON_RESUME")
    }

    override fun onActivityPaused(activity: Activity) {
        Log.d("OnPause","${activity.javaClass.simpleName} is ON_PAUSE")
    }

    override fun onActivityStopped(activity: Activity) {
        Log.d("OnStop","${activity.javaClass.simpleName} is ON_STOP")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        Log.d("OnDestroy","${activity.javaClass.simpleName} is ON_DESTROY")
    }
}