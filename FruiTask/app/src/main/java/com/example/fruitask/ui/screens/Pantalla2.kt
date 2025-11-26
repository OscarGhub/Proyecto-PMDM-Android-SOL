package com.example.fruitask.ui.screens

import android.content.Intent
import com.example.fruitask.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import android.net.Uri
import androidx.compose.material.icons.filled.MarkAsUnread
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Pantalla2(modifier: Modifier = Modifier) {

    var mostrandoFormulario by remember { mutableStateOf(false) }
    val runrun = rememberScrollState()
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(runrun)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Primera fila
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

                Text(
                    text = "0 / 100 XP",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(end = 12.dp)
                )
            }

            Spacer(Modifier.height(16.dp))

            // Imagen del Tamagochi
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.40f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sandia_fondo),
                    contentDescription = "Mascota",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(Modifier.height(16.dp))

            // Creación de tareas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Tareas", style = MaterialTheme.typography.titleLarge)
                    Text("¿Qué tareas tenemos?")
                }

                Button(onClick = { mostrandoFormulario = !mostrandoFormulario }) {
                    Text("Crear")
                }
            }

            Spacer(Modifier.height(16.dp))

            // Formulario de creación de tareas
            if (mostrandoFormulario) {

                var tituloTarea by remember { mutableStateOf("") }
                var descripcionTarea by remember { mutableStateOf("") }
                var tipoTarea by remember { mutableStateOf("") }
                var expanded by remember { mutableStateOf(false) }

                val opciones = listOf(
                    "Examen" to colorResource(id = R.color.purple_200),
                    "Proyecto" to colorResource(id = R.color.purple_700),
                    "Tarea" to colorResource(id = R.color.teal_200)
                )

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

                // Dropdown corregido
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = tipoTarea,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Tipo tarea") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        leadingIcon = {
                            val color =
                                opciones.find { it.first == tipoTarea }?.second ?: Color.Transparent
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .background(color, CircleShape)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        opciones.forEach { (nombre, color) ->
                            // Sintaxis corregida para Material 3
                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(12.dp)
                                                .background(color, CircleShape)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(nombre)
                                    }
                                },
                                onClick = {
                                    tipoTarea = nombre
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                Button(onClick = { /* seleccionar fecha */ }) {
                    Text("Seleccionar fecha")
                }

                Spacer(Modifier.height(20.dp))
            }

            // Lista de tareas
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 100.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                repeat(2) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp),
                        shape = RoundedCornerShape(16.dp)
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
                                Text(
                                    "Entregar proyecto PMDM",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Row(verticalAlignment = Alignment.CenterVertically) {
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

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Bottom), // hace que crezcan hacia arriba
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 210.dp, end = 20.dp) // empieza desde arriba del FAB
        ) {
            if (expanded) {
                // YouTube
                FloatingActionButton(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/"))
                        context.startActivity(intent)
                    },
                    containerColor = Color(0xFFFF0000),
                    contentColor = Color.White,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ChevronRight,
                        contentDescription = "Abrir Youtube"
                    )
                }

                // Correo
                FloatingActionButton(
                    onClick = {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:") // Solo apps de correo
                        }
                        context.startActivity(intent)
                    },
                    containerColor = Color(0xFF1DA1F2),
                    contentColor = Color.White,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.MarkAsUnread,
                        contentDescription = "Abrir Correo"
                    )
                }

                //  Instagram
                FloatingActionButton(
                    onClick = {
                        val intent =
                            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/"))
                        context.startActivity(intent)
                    },
                    containerColor = Color(0xFFC13584),
                    contentColor = Color.White,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.CameraAlt,
                        contentDescription = "Abrir Instagram"
                    )
                }
            }
        }

        // FAB principal
        FloatingActionButton(
            onClick = { expanded = !expanded },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 140.dp, end = 16.dp) // pequeño espacio desde la esquina
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Abrir redes"
            )
        }
    }
}