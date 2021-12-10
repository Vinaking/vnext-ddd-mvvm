package com.example.architecture.app

import android.app.Application
import android.util.Log

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("LIFECYCLELOG", "MyApplication onCreate")
    }
}