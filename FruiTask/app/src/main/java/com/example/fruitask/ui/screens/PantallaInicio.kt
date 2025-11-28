package com.example.fruitask.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.fruitask.data.local.database.MyViewModel

@Composable
fun PantallaInicio(viewModel: MyViewModel) {

    val activeFruit by viewModel.activeFruit.observeAsState()

    if (activeFruit == null) {

        // Opción A: Es la primera ejecución, no hay fruta guardada.
        // Muestra la pantalla de selección de personaje.
        PantallaEleccionPersonajes(
            onFrutaSeleccionada = { tipoFruta, nombreFruta ->
                viewModel.insertNewInitialFruit(nombreFruta, tipoFruta)
            }
        )
    } else {
        // Opción B: Ya existe una fruta.
        // Muestra la pantalla principal de juego.
        PantallaPrincipal(viewModel = viewModel)
    }
}