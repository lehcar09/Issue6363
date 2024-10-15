package com.google.dps

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyForegroundService : Service() {

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
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

        CoroutineScope(Dispatchers.IO).launch {
            delay(60000)
            throw IllegalStateException("Test error2") //This is of type java. lang. IllegalStateException
        }

        stopSelf()
        return START_STICKY
    }

    override fun stopService(name: Intent?): Boolean {
        Log.d("Stopping","Stopping Service")

        return super.stopService(name)
    }

    override fun onDestroy() {
        Toast.makeText(applicationContext, "Service execution completed", Toast.LENGTH_SHORT).show()
        Log.d("Stopped","Service Stopped")

        super.onDestroy()
    }

}