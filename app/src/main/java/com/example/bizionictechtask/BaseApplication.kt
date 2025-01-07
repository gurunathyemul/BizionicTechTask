package com.example.bizionictechtask

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: ")
        FirebaseApp.initializeApp(this)
    }

    companion object {
        private const val TAG = "BaseApplication"
    }
}