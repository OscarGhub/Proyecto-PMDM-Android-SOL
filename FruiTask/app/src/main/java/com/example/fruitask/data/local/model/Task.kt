package com.example.fruitask.data.local.model


import java.util.Date

data class Task(
    val id: Int = 0,
    val nombreTarea: String,
    val descripcionTarea: String,
    val tipoActividad: TipoActividad,
    val fechaActividad: Date,
    var estadoActividad: Boolean,
    var tareaHecha: Boolean,
    val fruitId: Int
)
