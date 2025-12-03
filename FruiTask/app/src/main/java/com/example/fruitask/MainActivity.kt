package com.example.fruitask

import Pantalla3
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.fruitask.data.local.database.MyViewModel
import com.example.fruitask.ui.components.NotificacionWorker
import com.example.fruitask.ui.screens.Pantalla1
import com.example.fruitask.ui.screens.Pantalla2
import com.example.fruitask.ui.screens.PantallaInicio
import com.example.fruitask.ui.screens.PantallaPrincipal
import com.example.fruitask.ui.theme.FruiTaskTheme
import java.util.concurrent.TimeUnit


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FruiTaskTheme {
                val viewModel: MyViewModel = viewModel()

                PantallaPrincipal(viewModel)
                Pantalla1(viewModel = viewModel)
                Pantalla2(viewModel = viewModel)
                Pantalla3(viewModel = viewModel)
                PantallaInicio(viewModel = viewModel)
            }
        }

        // Pedir permiso
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }
        }


        // Crear work request periódico cada 15 minutos
        val workRequest = PeriodicWorkRequestBuilder<NotificacionWorker>(15, TimeUnit.MINUTES)
            .build()

        // Encolar trabajo único, evita duplicados
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "notificacion_periodica",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}


