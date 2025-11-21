package com.example.fruitask.data.model;

import java.time.LocalDate

enum class TipoEvento {
    TAREA, EXAMEN, PROYECTO
}

data class Calendario (
    val fecha: LocalDate,
    val tipo: TipoEvento
)
