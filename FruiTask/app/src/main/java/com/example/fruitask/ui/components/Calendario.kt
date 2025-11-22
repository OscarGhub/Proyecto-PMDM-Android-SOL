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
                text = currentMonth.month.getDisplayName(TextStyle.FULL, locale)
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

        // CENTRAR LA GRILLA EN PANTALLA
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
            ) { actividad ->
                actividadSeleccionada = actividad
            }
        }

        // POPUP
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
    onDateSelected: (ActividadCalendario?) -> Unit
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
    onClick: (ActividadCalendario?) -> Unit
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

    val actividad = actividades.firstOrNull {
        it.year == date.year &&
                it.month == date.monthValue &&
                it.day == date.dayOfMonth
    }

    val actividadColor = when (actividad?.tipo) {
        TipoActividad.EXAMEN -> Color.Red.copy(alpha = 0.35f)
        TipoActividad.PROYECTO -> Color.Blue.copy(alpha = 0.35f)
        TipoActividad.TAREA -> Color.Green.copy(alpha = 0.35f)
        else -> Color.Transparent
    }

    val esHoy = date == hoy

    Box(
        modifier = Modifier
            .padding(4.dp)
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(
                if (actividad != null) actividadColor else Color.Transparent
            )
            .clickable { onClick(actividad) },
        contentAlignment = Alignment.Center
    ) {
        if (esHoy) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF9EC9FF).copy(alpha = 0.6f))
            )
        }

        Text(
            text = date.dayOfMonth.toString(),
            fontWeight = if (actividad != null || esHoy) FontWeight.Bold else FontWeight.Normal
        )
    }
}