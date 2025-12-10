package com.example.fruitask.data.local.model

data class ActividadCalendario(
    val year: Int,
    val month: Int,
    val day: Int,
    val tipo: TipoActividad,
    val titulo: String
)