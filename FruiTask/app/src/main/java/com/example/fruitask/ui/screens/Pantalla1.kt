package com.example.fruitask.ui.screens

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.livedata.observeAsState
import com.example.fruitask.R
import com.example.fruitask.data.local.database.MyViewModel
import com.example.fruitask.data.local.model.TipoActividad
import com.example.fruitask.data.local.model.TipoFruit
import com.example.fruitask.data.local.model.Task
import com.example.fruitask.ui.components.MascotaBailando
import com.example.fruitask.ui.components.getMensajeLogro
import com.example.fruitask.ui.theme.*

import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Pantalla1(modifier: Modifier = Modifier, viewModel: MyViewModel) {

    val activeFruit by viewModel.activeFruit.observeAsState()
    val tareas by viewModel.taskList.observeAsState(initial = emptyList())

    var mostrandoFormulario by remember { mutableStateOf(false) }
    val runrun = rememberScrollState()
    var expandedFab by remember { mutableStateOf(false) }
    val context = LocalContext.current

    var showDance by remember { mutableStateOf(false) }
    var mensajeLogro by remember { mutableStateOf<String?>(null) }

    var tituloTarea by remember { mutableStateOf("") }
    var descripcionTarea by remember { mutableStateOf("") }
    var tipoTareaStr by remember { mutableStateOf(TipoActividad.PROYECTO.name) }
    var expandedDropdown by remember { mutableStateOf(false) }

    var mostrarCalendario by remember { mutableStateOf(false) }
    var fechaSeleccionada by remember { mutableStateOf<Date?>(null) }
    val datePickerState = rememberDatePickerState(
        selectableDates = object : androidx.compose.material3.SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val hoy = System.currentTimeMillis()
                return utcTimeMillis >= hoy - 86_400_000
            }
        }
    )

    val opciones = listOf(
        TipoActividad.PROYECTO.name to ColorProyecto,
        TipoActividad.TAREA.name to ColorTarea,
        TipoActividad.EXAMEN.name to ColorExamen
    )

    val fruitType = activeFruit?.tipo
    val imageRes = when (fruitType) {
        TipoFruit.SANDIA -> R.drawable.sandia_fondo
        TipoFruit.KIWI -> R.drawable.kiwi_fondo
        TipoFruit.MANZANA -> R.drawable.manzana_fondo
        else -> R.drawable.sandia_fondo
    }

    val isFruitLoading = activeFruit == null

    //ESTADOS PARA EASTER EGG
    var clickCount by remember { mutableStateOf(0) }
    var showEasterEgg by remember { mutableStateOf(false) }



    Box(
        modifier = modifier
            .fillMaxSize()
            .background(VerdeFondo)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(runrun)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .height(60.dp)
                    .border(2.dp, VerdeBoton, RoundedCornerShape(12.dp)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = activeFruit?.nombre ?: "Cargando...",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 12.dp)
                )

                Surface(
                    shape = CircleShape,
                    color = VerdeBoton
                ) {
                    Box(
                        modifier = Modifier.size(36.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            activeFruit?.nivel?.toString() ?: "0",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

                Text(
                    text = "${activeFruit?.experiencia?.toInt() ?: 0} / 100 XP",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(end = 12.dp)
                )
            }

            Spacer(Modifier.height(16.dp))

            // BOX DE LA FRUTA CON EASTER EGG
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.40f)
                    .clickable {
                        clickCount++
                        if (clickCount >= 10) {
                            showEasterEgg = true
                            clickCount = 0

                            val mediaPlayer = MediaPlayer.create(context, R.raw.secreto)
                            mediaPlayer.start()
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                if (isFruitLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(50.dp))
                } else {
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = activeFruit?.nombre ?: "Mascota",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            //FORMULARIO DE TAREAS
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Tareas", style = MaterialTheme.typography.titleLarge)
                    Text("Tienes ${tareas.filter { !it.tareaHecha }.size} tareas pendientes.")
                }

                Button(
                    onClick = { mostrandoFormulario = !mostrandoFormulario },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VerdeBoton,
                        contentColor = Color.White
                    )
                ) {
                    Text(if (mostrandoFormulario) "Cancelar" else "Crear")
                }
            }

            Spacer(Modifier.height(16.dp))

            if (mostrandoFormulario) {

                OutlinedTextField(
                    value = tituloTarea,
                    onValueChange = { tituloTarea = it },
                    label = { Text("Título de la tarea", color = Color.DarkGray) },
                    textStyle = LocalTextStyle.current.copy(color = Color.Black),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedLabelColor = Color.DarkGray,
                        unfocusedLabelColor = Color.Gray,
                        focusedBorderColor = Color(0xFF333333),
                        unfocusedBorderColor = Color(0xFF555555),
                        cursorColor = Color.Black,

                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = descripcionTarea,
                    onValueChange = { descripcionTarea = it },
                    label = { Text("Descripción", color = Color.DarkGray) },
                    textStyle = LocalTextStyle.current.copy(color = Color.Black),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedLabelColor = Color.DarkGray,
                        unfocusedLabelColor = Color.Gray,
                        focusedBorderColor = Color(0xFF333333),
                        unfocusedBorderColor = Color(0xFF555555),
                        cursorColor = Color.Black,
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                ExposedDropdownMenuBox(
                    expanded = expandedDropdown,
                    onExpandedChange = { expandedDropdown = !expandedDropdown },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = tipoTareaStr,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Tipo tarea") },
                        textStyle = LocalTextStyle.current.copy(color = Color.Black),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedLabelColor = Color.DarkGray,
                            unfocusedLabelColor = Color.Gray,
                            focusedBorderColor = Color(0xFF333333),
                            unfocusedBorderColor = Color(0xFF555555),
                            cursorColor = Color.Black,
                        ),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDropdown) },
                        leadingIcon = {
                            val color =
                                opciones.find { it.first == tipoTareaStr }?.second
                                    ?: Color.Transparent
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
                        expanded = expandedDropdown,
                        onDismissRequest = { expandedDropdown = false },
                        containerColor = VerdeFondo

                    ) {
                        opciones.forEach { (nombre, color) ->
                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(12.dp)
                                                .background(color, CircleShape)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(nombre, color = Color.Black)
                                    }
                                },
                                onClick = {
                                    tipoTareaStr = nombre
                                    expandedDropdown = false
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = { mostrarCalendario = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VerdeBoton,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        fechaSeleccionada?.toString() ?: "Seleccionar fecha"
                    )
                }

                if (mostrarCalendario) {
                    DatePickerDialog(
                        onDismissRequest = { mostrarCalendario = false },
                        confirmButton = {
                            Button(onClick = {
                                val millis = datePickerState.selectedDateMillis
                                if (millis != null) fechaSeleccionada = Date(millis)
                                mostrarCalendario = false
                            }) {
                                Text("Aceptar")
                            }
                        },
                        dismissButton = {
                            Button(onClick = { mostrarCalendario = false }) {
                                Text("Cancelar")
                            }
                        }
                    ) {
                        DatePicker(state = datePickerState)
                    }
                }

                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = {
                        activeFruit?.let { fruit ->
                            if (tituloTarea.isNotBlank()) {
                                val newTask = Task(
                                    id = 0,
                                    nombreTarea = tituloTarea,
                                    descripcionTarea = descripcionTarea,
                                    tipoActividad = TipoActividad.valueOf(tipoTareaStr),
                                    fechaActividad = fechaSeleccionada ?: Date(),
                                    estadoActividad = false,
                                    tareaHecha = false,
                                    fruitId = fruit.id
                                )
                                viewModel.insertTask(newTask)
                                mostrandoFormulario = false
                                tituloTarea = ""
                                descripcionTarea = ""
                                fechaSeleccionada = null
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VerdeBoton,
                        contentColor = Color.White
                    ),
                    enabled = activeFruit != null && tituloTarea.isNotBlank()
                ) {
                    Text("Guardar Tarea")
                }

                Spacer(Modifier.height(20.dp))
            }

            // ⬇⬇⬇ LISTA DE TAREAS ⬇⬇⬇
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 100.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val tareasPendientes = tareas.filter { !it.tareaHecha }

                if (tareasPendientes.isEmpty()) {
                    Text(
                        "¡Todo al día! No tienes tareas pendientes.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    tareasPendientes.forEach { task ->
                        val colorTipo = when (task.tipoActividad) {
                            TipoActividad.PROYECTO -> ColorProyecto
                            TipoActividad.TAREA -> ColorTarea
                            TipoActividad.EXAMEN -> ColorExamen
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = VerdeBoton
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(14.dp)
                                            .background(colorTipo, CircleShape)
                                    )

                                    Spacer(Modifier.width(10.dp))

                                    Column {
                                        Text(
                                            text = task.nombreTarea,
                                            style = MaterialTheme.typography.titleMedium,
                                            color = Color.Black
                                        )

                                        Text(
                                            text = task.descripcionTarea,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color.DarkGray
                                        )

                                        Text(
                                            text = task.tipoActividad.name,
                                            color = colorTipo,
                                            style = MaterialTheme.typography.labelMedium
                                        )

                                        task.fechaActividad?.let { fecha ->
                                            Text(
                                                text = android.text.format.DateFormat.format("dd/MM/yyyy", fecha).toString(),
                                                style = MaterialTheme.typography.bodySmall,
                                                color = Color.DarkGray
                                            )
                                        }
                                    }
                                }

                                Icon(
                                    imageVector = Icons.Filled.Check,
                                    contentDescription = "Completar Tarea",
                                    tint = Negrocheck,
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clickable {
                                            viewModel.completarTarea(task) { subeNivel ->
                                                if (subeNivel) {
                                                    val nivelNuevo = activeFruit?.nivel ?: 0
                                                    mensajeLogro = getMensajeLogro(
                                                        activeFruit?.tipo,
                                                        nivelNuevo
                                                    )
                                                    showDance = true
                                                }
                                            }
                                        }
                                )
                            }
                        }
                    }
                }
            }
        }

        // ⬇⬇⬇ FABs ⬇⬇⬇
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Bottom),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 210.dp, end = 20.dp)
        ) {
            if (expandedFab) {

                FloatingActionButton(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/"))
                        context.startActivity(intent)
                    },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ChevronRight,
                        contentDescription = "Abrir Youtube"
                    )
                }

                FloatingActionButton(
                    onClick = {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:")
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

        FloatingActionButton(
            onClick = { expandedFab = !expandedFab },
            containerColor = VerdeBoton,
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 140.dp, end = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Abrir redes"
            )
        }

        // ⬇⬇⬇ DIALOG DEL EASTER EGG ⬇⬇⬇
        if (showEasterEgg) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "¡Vamos, suerte estudiando!",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(onClick = { showEasterEgg = false }) {
                            Text("Cerrar")
                        }
                    }
                }
            }
        }

        // ⬇⬇⬇ Mascota bailandooo ⬇⬇⬇
        if (showDance) {
            val videoRes = when (activeFruit?.tipo) {
                TipoFruit.KIWI -> R.raw.kiwi_dance
                TipoFruit.MANZANA -> R.raw.manzana_dance
                TipoFruit.SANDIA -> R.raw.sandia_dance
                else -> R.raw.kiwi_dance
            }

            MascotaBailando(
                videoRes = videoRes,
                message = mensajeLogro,
                onFinished = {
                    showDance = false
                    mensajeLogro = null
                }
            )
        }
    }
}
