package com.google.dps

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

class NotificationUtils (
    var context: Context
){

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(
            "My service", "Foreground Service Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = context.getSystemService(NotificationManager::class.java)
        manager!!.createNotificationChannel(serviceChannel)
    }


    fun createNotification(service: Service) {
        val notification = NotificationCompat.Builder(context, "My service")
            .setContentTitle(context.resources.getString(R.string.app_name))
            .setColor(context.resources.getColor(R.color.colorAccent, null))
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setOngoing(true)
            .build()
        service.startForeground(1, notification)
    }
}