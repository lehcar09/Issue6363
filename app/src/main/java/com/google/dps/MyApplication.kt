package com.google.dps

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.crashlytics
import com.google.firebase.database.FirebaseDatabase

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
        Firebase.crashlytics.setCustomKey("issue6363-1", true) //can not provide key and values details
        Firebase.crashlytics.setCustomKey("issue6363-2", false) //can not provide key and values details
        Firebase.crashlytics.setUserId("12345") //can not provide my number details
        FirebaseDatabase.getInstance().setPersistenceEnabled(false)
    }

}