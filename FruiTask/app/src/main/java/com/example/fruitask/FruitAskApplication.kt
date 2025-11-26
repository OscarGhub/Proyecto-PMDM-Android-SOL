package com.example.fruitask

import android.app.Application
import com.example.fruitask.data.local.database.AppContainer
import com.example.fruitask.data.local.database.DefaultAppContainer

class FruiTaskApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()

        // 1. Inicializamos el contenedor de dependencias.
        //    Esto, a su vez, inicializa la base de datos de Room a través de DefaultAppContainer.
        container = DefaultAppContainer(this)
    }
}