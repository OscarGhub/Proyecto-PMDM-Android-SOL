package com.example.fruitask.ui.components

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.fruitask.R

class NotificacionWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {

        val channelId = "canal_notificacion"
        val channelName = "Notificaciones periódicas"
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Crear canal solo si no existe (Android 8+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager.getNotificationChannel(channelId) == null) {
                val channel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationManager.createNotificationChannel(channel)
            }
        }

        // Construir notificación
        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle("Recordatorio")
            .setContentText("Recuerda hacer las tareas eh.")
            // ⚠️ Usar icono monocromo, blanco sobre transparente
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setAutoCancel(true)
            .build()

        // Mostrar notificación
        notificationManager.notify(1, notification)

        return Result.success()
    }
}
