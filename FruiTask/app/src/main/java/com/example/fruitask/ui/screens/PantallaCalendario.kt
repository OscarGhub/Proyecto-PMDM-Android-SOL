package com.example.fruitask.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.fruitask.ui.components.*

@Composable
fun PantallaCalendario(modifier: Modifier = Modifier) {

    val actividades = listOf(
        ActividadCalendario(2025, 11, 10, TipoActividad.EXAMEN, "Examen de Matemáticas"),
        ActividadCalendario(2025, 11, 14, TipoActividad.TAREA, "Entrega de tarea de Historia"),
        ActividadCalendario(2025, 11, 22, TipoActividad.PROYECTO, "Proyecto de Ciencias")
    )

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Calendario",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Calendario(actividades = actividades)
    }
}