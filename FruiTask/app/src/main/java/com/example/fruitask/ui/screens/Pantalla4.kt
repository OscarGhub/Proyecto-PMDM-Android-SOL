package com.example.fruitask.ui.screens

import android.os.CountDownTimer
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Pantalla4(modifier: Modifier = Modifier) {

    // Valores seleccionados
    var horas by remember { mutableStateOf("0") }
    var minutos by remember { mutableStateOf("0") }
    var segundos by remember { mutableStateOf("0") }

    // Listas para elegir 0..59
    val listaHoras = (0..23).map { it.toString() }
    val listaMinSeg = (0..59).map { it.toString() }

    // Estados del dropdown
    var horasExpandido by remember { mutableStateOf(false) }
    var minutosExpandido by remember { mutableStateOf(false) }
    var segundosExpandido by remember { mutableStateOf(false) }

    // Temporizador
    var tiempoRestante by remember { mutableLongStateOf(0L) }
    var temporizadorActivo by remember { mutableStateOf(false) }
    var timer: CountDownTimer? by remember { mutableStateOf(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Temporizador", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(20.dp))

        // ------------------- HORAS -------------------
        ExposedDropdownMenuBox(
            expanded = horasExpandido,
            onExpandedChange = { horasExpandido = it }
        ) {
            OutlinedTextField(
                value = horas,
                onValueChange = {},
                readOnly = true,
                label = { Text("Horas") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = horasExpandido) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = horasExpandido,
                onDismissRequest = { horasExpandido = false }
            ) {
                listaHoras.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            horas = opcion
                            horasExpandido = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ------------------- MINUTOS -------------------
        ExposedDropdownMenuBox(
            expanded = minutosExpandido,
            onExpandedChange = { minutosExpandido = it }
        ) {
            OutlinedTextField(
                value = minutos,
                onValueChange = {},
                readOnly = true,
                label = { Text("Minutos") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = minutosExpandido) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = minutosExpandido,
                onDismissRequest = { minutosExpandido = false }
            ) {
                listaMinSeg.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            minutos = opcion
                            minutosExpandido = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ------------------- SEGUNDOS -------------------
        ExposedDropdownMenuBox(
            expanded = segundosExpandido,
            onExpandedChange = { segundosExpandido = it }
        ) {
            OutlinedTextField(
                value = segundos,
                onValueChange = {},
                readOnly = true,
                label = { Text("Segundos") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = segundosExpandido) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = segundosExpandido,
                onDismissRequest = { segundosExpandido = false }
            ) {
                listaMinSeg.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            segundos = opcion
                            segundosExpandido = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ------------ BOTÓN INICIAR ----------------
        Button(
            onClick = {
                val totalSegundos =
                    horas.toLong() * 3600 +
                            minutos.toLong() * 60 +
                            segundos.toLong()

                if (totalSegundos > 0) {
                    tiempoRestante = totalSegundos

                    timer?.cancel()
                    timer = object : CountDownTimer(totalSegundos * 1000, 1000) {
                        override fun onTick(ms: Long) {
                            tiempoRestante = ms / 1000
                        }

                        override fun onFinish() {
                            tiempoRestante = 0
                            temporizadorActivo = false
                        }
                    }.start()

                    temporizadorActivo = true
                }
            },
            enabled = !temporizadorActivo,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar")
        }

        Spacer(modifier = Modifier.height(30.dp))

        // ------------ MOSTRAR TIEMPO RESTANTE --------------
        val h = tiempoRestante / 3600
        val m = (tiempoRestante % 3600) / 60
        val s = tiempoRestante % 60

        Text(
            text = "Tiempo restante: %02d:%02d:%02d".format(h, m, s),
            style = MaterialTheme.typography.headlineLarge
        )
    }
}
