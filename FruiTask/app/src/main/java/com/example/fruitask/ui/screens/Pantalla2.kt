package com.example.fruitask.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Pantalla2(modifier: Modifier = Modifier) {

    // --- Estado del diálogo ---
    var showDatePicker by remember { mutableStateOf(false) }

    // --- Estado para guardar la fecha seleccionada ---
    var selectedDateMillis by remember { mutableStateOf<Long?>(null) }

    // --- Formato para mostrar la fecha ---
    val dateFormatter = remember {
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    }

    var textoTituloTarea by remember { mutableStateOf("") }
    var textoContenidoTarea by remember { mutableStateOf("") }




    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Ingresa el titulo de la tarea")


        OutlinedTextField(
            value = textoTituloTarea,
            onValueChange = { textoTituloTarea = it },
            label = { Text("Nombre de la tarea") },
            placeholder = { Text("Introduce tu nombre") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text("Ingresa la descripción de la tarea")


        OutlinedTextField(
            value = textoContenidoTarea,
            onValueChange = { textoContenidoTarea = it },
            label = { Text("Contenido") },
            placeholder = { Text("Introduce tu nombre") }
        )

        // Botón para abrir DatePickerDialog
        Button(onClick = { showDatePicker = true }) {
            Text("Seleccionar fecha")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Mostrar fecha seleccionada
        selectedDateMillis?.let {
            val formatted = dateFormatter.format(Date(it))
            Text("Fecha seleccionada: $formatted")
        }
    }

    // ------------------- DatePickerDialog -------------------
    if (showDatePicker) {

        val datePickerState = rememberDatePickerState()

        DatePickerDialog(
            onDismissRequest = {
                showDatePicker = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        selectedDateMillis = datePickerState.selectedDateMillis
                        showDatePicker = false
                    }
                ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDatePicker = false }
                ) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
