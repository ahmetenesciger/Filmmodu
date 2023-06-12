package com.example.filmmodu.application

import android.app.Application
import android.util.Log
import com.example.filmmodu.BuildConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GenericApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            
        }
    }
}