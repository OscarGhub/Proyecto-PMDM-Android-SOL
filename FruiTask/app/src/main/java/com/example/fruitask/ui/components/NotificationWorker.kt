package com.example.fruitask.ui.components

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.fruitask.R

class NotificacionWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {

        val channelId = "canal_notificacion"
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Crear canal
        val channel = NotificationChannel(
            channelId,
            "Notificaciones periódicas",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)

        // Construir notificación
        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle("Recordatorio")
            .setContentText("Esta es una notificación enviada automáticamente.")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        // Mostrar notificación
        notificationManager.notify(1, notification)

        return Result.success()
    }
}
