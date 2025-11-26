package com.example.fruitask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.fruitask.ui.screens.PantallaPrincipal
import com.example.fruitask.ui.theme.FruiTaskTheme
import com.example.fruitask.viewmodel.FruitViewModel
import com.example.fruitask.viewmodel.FruitViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // --- CONEXIÓN DE ROOM Y VIEWMODEL ---

        // 1. Obtener la instancia de FruiTaskApplication
        val application = application as FruiTaskApplication

        // 2. Obtener el contenedor de dependencias (AppContainer)
        val container = application.container

        // Esto le permite a Android crear el ViewModel con sus dependencias.
        val factory = FruitViewModelFactory(container.fruitRepository)

        setContent {
            FruiTaskTheme {
                // Le pasamos la Factory para que se encargue de la creación del ViewModel.
                MainAppScreen(factory = factory)
            }
        }
    }
}

// Función Composible que se encarga de crear y exponer el ViewModel al árbol de la UI
@Composable
fun MainAppScreen(factory: FruitViewModelFactory) {

    val viewModel: FruitViewModel = viewModel(factory = factory)

    // Pasamos el ViewModel a la PantallaPrincipal para que pueda acceder a los datos y guardar.
    PantallaPrincipal(viewModel = viewModel)
}