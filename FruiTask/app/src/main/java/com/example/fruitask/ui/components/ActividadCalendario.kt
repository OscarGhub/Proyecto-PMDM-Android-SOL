package com.example.fruitask.ui.components

import com.example.fruitask.data.local.model.TipoActividad


data class ActividadCalendario(
    val year: Int,
    val month: Int,
    val day: Int,
    val tipo: TipoActividad,
    val titulo: String
)