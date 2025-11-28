package com.example.fruitask.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.MarkAsUnread
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
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
import com.example.fruitask.ui.theme.ColorCompletada
import com.example.fruitask.ui.theme.ColorExamen
import com.example.fruitask.ui.theme.ColorPendiente
import com.example.fruitask.ui.theme.ColorProyecto
import com.example.fruitask.ui.theme.ColorTarea
import com.example.fruitask.ui.theme.VerdeBoton
import com.example.fruitask.ui.theme.VerdeFondo
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Pantalla1(modifier: Modifier = Modifier, viewModel: MyViewModel) {

    // 1. OBSERVACIÓN DE DATOS
    val activeFruit by viewModel.activeFruit.observeAsState()
    val tareas by viewModel.taskList.observeAsState(initial = emptyList())

    // Estados UI
    var mostrandoFormulario by remember { mutableStateOf(false) }
    val runrun = rememberScrollState()
    var expandedFab by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // ESTADO PARA MOSTRAR EL VIDEO DE BAILE
    var showDance by remember { mutableStateOf(false) }

    //ESTADO PARA MOSTRAR EL MENSAJE DE LOGRO
    var mensajeLogro by remember { mutableStateOf<String?>(null) }


    // 2. ESTADOS DEL FORMULARIO
    var tituloTarea by remember { mutableStateOf("") }
    var descripcionTarea by remember { mutableStateOf("") }
    var tipoTareaStr by remember { mutableStateOf(TipoActividad.PROYECTO.name) }
    var expandedDropdown by remember { mutableStateOf(false) }

    // 3. OPCIONES ACTUALIZADAS
    val opciones = listOf(
        TipoActividad.PROYECTO.name to ColorProyecto,
        TipoActividad.TAREA.name to ColorTarea,
        TipoActividad.EXAMEN.name to ColorExamen
    )

    // 4. LÓGICA DINÁMICA DE IMAGEN
    val fruitType = activeFruit?.tipo
    val imageRes = when (fruitType) {
        TipoFruit.SANDIA -> R.drawable.sandia_fondo
        TipoFruit.KIWI -> R.drawable.kiwi_fondo
        TipoFruit.MANZANA -> R.drawable.manzana_fondo
        else -> R.drawable.sandia_fondo
    }

    // Si activeFruit es null (lo que no debería pasar si PantallaInicio funciona), mostramos un loading
    val isFruitLoading = activeFruit == null

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
            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .height(60.dp)
                    .border(2.dp, VerdeBoton, RoundedCornerShape(12.dp)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Nombre
                Text(
                    text = activeFruit?.nombre ?: "Cargando...",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 12.dp)
                )

                // Nivel
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

                // Experiencia
                Text(
                    text = "${activeFruit?.experiencia?.toInt() ?: 0} / 100 XP",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(end = 12.dp)
                )
            }

            Spacer(Modifier.height(16.dp))

            // Imagen del Fruit (DINÁMICA)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.40f),
                contentAlignment = Alignment.Center
            ) {
                if (isFruitLoading) {
                    // Muestra un indicador de carga si la fruta aún no está disponible
                    CircularProgressIndicator(modifier = Modifier.size(50.dp))
                } else {
                    Image(
                        painter = painterResource(id = imageRes), // USA EL RECURSO DINÁMICO
                        contentDescription = activeFruit?.nombre ?: "Mascota",
                        modifier = Modifier.fillMaxSize()
                    )
                }
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
                    Text("Tienes ${tareas.filter { !it.tareaHecha }.size} tareas pendientes.")
                }

                Button(onClick = { mostrandoFormulario = !mostrandoFormulario }, colors = ButtonDefaults.buttonColors(
                    containerColor = VerdeBoton,
                    contentColor = Color.White
                )) {
                    Text(if (mostrandoFormulario) "Cancelar" else "Crear")
                }
            }

            Spacer(Modifier.height(16.dp))

            // Formulario de creación de tareas
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

                // Dropdown con los tipos de actividad
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
                        onDismissRequest = { expandedDropdown = false }

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
                    onClick = { /* seleccionar fecha */ }, colors = ButtonDefaults.buttonColors(
                        containerColor = VerdeBoton,
                        contentColor = Color.White
                    )
                ) {
                    Text("Seleccionar fecha")
                }

                Spacer(Modifier.height(12.dp))

                // Lógica de Guardar Tarea
                Button(
                    onClick = {
                        activeFruit?.let { fruit ->
                            if (tituloTarea.isNotBlank()) {
                                val newTask = Task(
                                    id = 0,
                                    nombreTarea = tituloTarea,
                                    descripcionTarea = descripcionTarea,
                                    tipoActividad = TipoActividad.valueOf(tipoTareaStr),
                                    fechaActividad = Date(),
                                    estadoActividad = false,
                                    tareaHecha = false,
                                    fruitId = fruit.id
                                )
                                viewModel.insertTask(newTask)
                                mostrandoFormulario = false
                                tituloTarea = ""
                                descripcionTarea = ""
                            }
                        }
                    },colors = ButtonDefaults.buttonColors(
                        containerColor = VerdeBoton,
                        contentColor = Color.White
                    ),
                    enabled = activeFruit != null && tituloTarea.isNotBlank()
                ) {
                    Text("Guardar Tarea")
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


                        // TARJETA
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            shape = RoundedCornerShape(16.dp)
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
                                    }
                                }

                                //ICONO DE COMPLETAR
                                Icon(
                                    imageVector = Icons.Filled.Check,
                                    contentDescription = "Completar Tarea",
                                    tint = VerdeBoton,
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

        // FABs
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Bottom),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 210.dp, end = 20.dp)
        ) {
            if (expandedFab) {
                // YouTube
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

                // Correo
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

                // Instagram
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

        //POPUP CON VIDEO DEL BAILE
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