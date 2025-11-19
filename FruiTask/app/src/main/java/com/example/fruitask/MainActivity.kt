package com.example.fruitask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.fruitask.ui.screens.PantallaPrincipal
import com.example.fruitask.ui.theme.FruiTaskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FruiTaskTheme {

                PantallaPrincipal()
            }
        }
    }
}
