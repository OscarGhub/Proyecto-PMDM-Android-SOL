package com.example.fruitask

import Pantalla3
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fruitask.data.local.database.MyViewModel
import com.example.fruitask.ui.screens.Pantalla1
import com.example.fruitask.ui.screens.Pantalla2
import com.example.fruitask.ui.screens.PantallaInicio
import com.example.fruitask.ui.screens.PantallaPrincipal
import com.example.fruitask.ui.theme.FruiTaskTheme

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
    }
}