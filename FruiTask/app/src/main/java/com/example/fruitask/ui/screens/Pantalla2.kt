package com.example.fruitask.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fruitask.R
import com.example.fruitask.data.local.database.MyViewModel
import com.example.fruitask.data.local.model.TipoActividad as TipoActividadModel
import com.example.fruitask.data.local.model.Task
import com.example.fruitask.data.local.model.ActividadCalendario
import com.example.fruitask.ui.components.Calendario
import com.example.fruitask.ui.theme.VerdeFondo
import java.util.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Pantalla2(modifier: Modifier = Modifier, viewModel: MyViewModel) {

    // Obtenemos todas las tareas desde el ViewModel
    val tareas by viewModel.taskList.observeAsState(initial = emptyList())

    // Filtramos solo tareas pendientes para mostrarlas en el calendario
    val actividades = tareas.filter { !it.tareaHecha }.map { tarea: Task ->
        val cal = Calendar.getInstance().apply { time = tarea.fechaActividad ?: Date() }

        val tipoCalendario = when (tarea.tipoActividad) {
            TipoActividadModel.EXAMEN -> TipoActividadModel.EXAMEN
            TipoActividadModel.PROYECTO -> TipoActividadModel.PROYECTO
            TipoActividadModel.TAREA -> TipoActividadModel.TAREA
        }

        ActividadCalendario(
            year = cal.get(Calendar.YEAR),
            month = cal.get(Calendar.MONTH) + 1, // Calendar.MONTH es 0-based
            day = cal.get(Calendar.DAY_OF_MONTH),
            tipo = tipoCalendario,
            titulo = tarea.nombreTarea
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(VerdeFondo),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.calendarioletrero),
                contentDescription = "Mascota",
                modifier = Modifier.fillMaxSize()
            )
        }

        // Mostramos el calendario con las tareas pendientes
        Calendario(actividades = actividades)
    }
}
