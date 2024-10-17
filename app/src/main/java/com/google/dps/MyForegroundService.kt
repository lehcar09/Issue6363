package com.google.dps

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.dps.MyApplication.Companion.mApplicationContext
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyForegroundService : Service() {

    var defaultUEH: Thread.UncaughtExceptionHandler? = null
    private lateinit var notificationUtils: NotificationUtils
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        Log.d("dudi","On create called")
        uncaughtExceptionInitialization()
        notificationUtils = NotificationUtils(mApplicationContext!!)
        notificationUtils.createNotificationChannel()
        notificationUtils.createNotification(this)
        CoroutineScope(Dispatchers.IO).launch {
            delay(30000)
            throw ExceptionInInitializerError("Hii 19.2.0 error") //This is of type java. lang. IllegalStateException
        }
    }

    fun restartService() {
        val service = PendingIntent.getService(
            applicationContext,
            1012,
            Intent(applicationContext, MyForegroundService::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager[AlarmManager.ELAPSED_REALTIME_WAKEUP, 4000] = service
        Runtime.getRuntime().exit(0)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val name=intent?.getStringExtra("name")

        Toast.makeText(applicationContext, "Service has started running in the background", Toast.LENGTH_SHORT).show()

        if (name != null) {
            Log.d("Service Name",name)
        }

        Log.d("Service Status","Starting Service")

        for (i in 1..10) {
            Thread.sleep(100)
            Log.d("Status", "Service $i")
        }

        return START_STICKY
    }

    override fun stopService(name: Intent?): Boolean {
        Log.d("Stopping","Stopping Service")

        return super.stopService(name)
    }

    fun uncaughtExceptionInitialization() {
        defaultUEH = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(unCaughtExceptionHandler)
    }

    var unCaughtExceptionHandler =
        Thread.UncaughtExceptionHandler { _, ex ->
            Log.w("dudi","Uncaught Exception Handler: caught exception ->", ex)
            Firebase.crashlytics.recordException(ex)
            restartService()
        }

    override fun onDestroy() {
        Toast.makeText(applicationContext, "Service execution completed", Toast.LENGTH_SHORT).show()
        Log.d("Stopped","Service Stopped")

        super.onDestroy()
    }

}