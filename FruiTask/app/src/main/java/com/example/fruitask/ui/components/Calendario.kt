package com.example.fruitask.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fruitask.data.local.model.ActividadCalendario
import com.example.fruitask.data.local.model.TipoActividad
import com.example.fruitask.ui.theme.ColorExamen
import com.example.fruitask.ui.theme.ColorProyecto
import com.example.fruitask.ui.theme.ColorTarea
import com.example.fruitask.ui.theme.VerdeBoton
import com.example.fruitask.ui.theme.VerdeFondo
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale


@Composable
fun Calendario(
    actividades: List<ActividadCalendario>,
    modifier: Modifier = Modifier
) {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var actividadSeleccionada by remember { mutableStateOf<ActividadCalendario?>(null) }
    var actividadesDelDia by remember { mutableStateOf<List<ActividadCalendario>>(emptyList()) }
    var mostrarLista by remember { mutableStateOf(false) }

    val locale = Locale("es", "ES")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // HEADER
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Anterior",
                modifier = Modifier
                    .size(30.dp)
                    .weight(1f)
                    .clickable { currentMonth = currentMonth.minusMonths(1) },
                tint = Color.Black
            )

            Text(
                text = currentMonth.month
                    .getDisplayName(TextStyle.FULL, locale)
                    .replaceFirstChar { it.uppercase() } + " " + currentMonth.year,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(2f),
                textAlign = TextAlign.Center
            )

            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Siguiente",
                modifier = Modifier
                    .size(30.dp)
                    .weight(1f)
                    .clickable { currentMonth = currentMonth.plusMonths(1) },
                tint = Color.Black
            )
        }

        // DÍAS DE LA SEMANA
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            DayOfWeek.values().forEach {
                Text(
                    it.getDisplayName(TextStyle.SHORT, locale).uppercase(),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // GRID DEL MES
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            MonthGrid(
                month = currentMonth,
                actividades = actividades,
                modifier = Modifier.wrapContentWidth(),
            ) { actividadesDia: List<ActividadCalendario> ->

                when (actividadesDia.size) {
                    0 -> {}
                    1 -> actividadSeleccionada = actividadesDia.first()
                    else -> {
                        actividadesDelDia = actividadesDia
                        mostrarLista = true
                    }
                }
            }
        }

        // LISTA ACTIVIDADES
        if (mostrarLista) {
            AlertDialog(
                containerColor = VerdeFondo,
                titleContentColor = Color.Black,
                textContentColor = Color.Black,
                onDismissRequest = { mostrarLista = false },

                title = {
                    Text(
                        text = "Actividades del día",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },

                text = {
                    Column {
                        actividadesDelDia.forEach { act ->
                            Text(
                                text = "• ${act.titulo}",
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clickable {
                                        actividadSeleccionada = act
                                        mostrarLista = false
                                    }
                            )
                        }
                    }
                },

                confirmButton = {
                    Button(
                        onClick = { mostrarLista = false },
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = VerdeBoton,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Cerrar")
                    }
                }
            )
        }

        // DIALOGO INDIVIDUAL ---------------------------------
        actividadSeleccionada?.let { actividad ->
            AlertDialog(
                containerColor = VerdeFondo,
                titleContentColor = Color.Black,
                textContentColor = Color.Black,
                onDismissRequest = { actividadSeleccionada = null },

                title = {
                    Text(
                        text = actividad.titulo,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },

                text = {
                    Text(
                        "Fecha: ${actividad.day}/${actividad.month}/${actividad.year}\n" +
                                "Tipo: ${actividad.tipo}"
                    )
                },

                confirmButton = {
                    Button(
                        onClick = { actividadSeleccionada = null },
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = VerdeBoton,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Cerrar")
                    }
                }
            )
        }
    }
}

@Composable
fun MonthGrid(
    month: YearMonth,
    actividades: List<ActividadCalendario>,
    modifier: Modifier = Modifier,
    onDateSelected: (List<ActividadCalendario>) -> Unit
) {
    val firstDay = month.atDay(1)
    val lastDay = month.atEndOfMonth()

    val firstDayOfWeek = firstDay.dayOfWeek.value % 7
    val daysInMonth = (1..lastDay.dayOfMonth).toList()

    val calendarCells = buildList<LocalDate?> {
        repeat(firstDayOfWeek - 1) { add(null) }
        daysInMonth.forEach { day -> add(month.atDay(day)) }
        while (size % 7 != 0) add(null)
    }

    Column(
        modifier = modifier.padding(8.dp)
    ) {
        calendarCells.chunked(7).forEach { week ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                week.forEach { date ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                    ) {
                        Dia(
                            date = date,
                            actividades = actividades,
                            onClick = onDateSelected
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Dia(
    date: LocalDate?,
    actividades: List<ActividadCalendario>,
    onClick: (List<ActividadCalendario>) -> Unit
) {
    if (date == null) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .padding(4.dp)
        )
        return
    }

    val hoy = LocalDate.now()

    val actividadesDelDia = actividades.filter {
        it.year == date.year &&
                it.month == date.monthValue &&
                it.day == date.dayOfMonth
    }

    val tieneVarias = actividadesDelDia.size > 1
    val tieneUna = actividadesDelDia.size == 1

    val actividadColor = if (tieneUna) {
        when (actividadesDelDia.first().tipo) {
            TipoActividad.EXAMEN -> ColorExamen
            TipoActividad.PROYECTO -> ColorProyecto
            TipoActividad.TAREA -> ColorTarea
            else -> Color.Transparent
        }
    } else Color.Transparent

    val esHoy = date == hoy

    Box(
        modifier = Modifier
            .padding(4.dp)
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(actividadColor)
            .clickable {
                if (actividadesDelDia.isNotEmpty()) {
                    onClick(actividadesDelDia)
                }
            },
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Número del día
            Text(
                text = date.dayOfMonth.toString(),
                fontWeight = if (tieneUna || esHoy) FontWeight.Bold else FontWeight.Normal
            )

            // Puntitos si hay varias actividades
            if (tieneVarias) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    actividadesDelDia.forEach { actividad ->
                        val color = when (actividad.tipo) {
                            TipoActividad.EXAMEN -> ColorExamen
                            TipoActividad.PROYECTO -> ColorProyecto
                            TipoActividad.TAREA -> ColorTarea
                            else -> Color.Transparent
                        }

                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(color)
                        )

                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }
            }
        }

        // Hoy
        if (esHoy) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF9EC9FF).copy(alpha = 0.6f))
            )
        }
    }
}