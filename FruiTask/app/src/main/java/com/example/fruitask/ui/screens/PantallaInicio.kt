package com.example.fruitask.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun PantallaInicio() {
    // Estado que guarda la fruta seleccionada; null = aún no se ha elegido
    var frutaSeleccionada by remember { mutableStateOf<String?>(null) }

    if (frutaSeleccionada == null) {
        // Todavía no se ha elegido fruta → mostrar selección
        PantallaEleccionPersonajes(
            onFrutaSeleccionada = { fruta ->
                frutaSeleccionada = fruta // ⚡ Cambiamos el estado → esto disparará el else
            }
        )
    } else {
        // Ya se eligió fruta → mostrar la pantalla principal con menú
        PantallaPrincipal()
    }
}


