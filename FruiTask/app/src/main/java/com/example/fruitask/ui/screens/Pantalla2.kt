package com.example.fruitask.ui.screens

import com.example.fruitask.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Pantalla2(modifier: Modifier = Modifier) {

    var mostrandoFormulario by remember { mutableStateOf(false) }
    val runrun = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize().verticalScroll(runrun)
            .padding(16.dp)

    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // ---------------------- FILA SUPERIOR ----------------------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .height(60.dp)
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "(Nombre)",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 12.dp)
            )

            // Nivel
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary
            ) {
                Box(
                    modifier = Modifier.size(36.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("1", color = MaterialTheme.colorScheme.onPrimary)
                }
            }

            // Experiencia
            Text(
                text = "0 / 100 XP",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(end = 12.dp)
            )
        }

        Spacer(Modifier.height(16.dp))

        // ---------------------- IMAGEN DE LA MASCOTA ----------------------
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.40f),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.sandi_pancho), // asegúrate de que este drawable exista
                contentDescription = "Mascota",
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(Modifier.height(16.dp))

        // ---------------------- FILA TAREAS + BOTÓN CREAR ----------------------
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Tareas", style = MaterialTheme.typography.titleLarge)
                Text("¿Qué tareas tenemos?")
            }

            Button(
                onClick = { mostrandoFormulario = !mostrandoFormulario }
            ) {
                Text("Crear")
            }
        }

        Spacer(Modifier.height(16.dp))

        // ---------------------- FORMULARIO (SOLO CUANDO SE CREA) ----------------------
        if (mostrandoFormulario) {

            var tituloTarea by remember { mutableStateOf("") }
            var descripcionTarea by remember { mutableStateOf("") }

            OutlinedTextField(
                value = tituloTarea,
                onValueChange = { tituloTarea = it },
                label = { Text("Título de la tarea") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = descripcionTarea,
                onValueChange = { descripcionTarea = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            Button(onClick = {}) {
                Text("Seleccionar fecha")
            }

            Spacer(Modifier.height(20.dp))
        }

        // ---------------------- LISTA DE TAREAS (VISUAL) ----------------------
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            repeat(2) { indice ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column {
                            Text("Proyecto PMDM", style = MaterialTheme.typography.titleMedium)
                            Text("Entregar proyecto PMDM", style = MaterialTheme.typography.bodyMedium)

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Filled.CalendarToday, contentDescription = null)
                                Spacer(Modifier.width(4.dp))
                                Text("11/12/2025")
                            }
                        }

                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Completar",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }
    }
}
