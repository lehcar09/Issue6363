package com.google.dps

import android.app.Application
import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.crashlytics

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        mApplicationContext = applicationContext
        FirebaseApp.initializeApp(this)
        Firebase.crashlytics.setCustomKey("issue6363-1", true) //can not provide key and values details
        Firebase.crashlytics.setCustomKey("issue6363-2", false) //can not provide key and values details
        Firebase.crashlytics.setUserId("12345") //can not provide my number details
    }

    companion object {
        var mApplicationContext: Context? = null
    }
}