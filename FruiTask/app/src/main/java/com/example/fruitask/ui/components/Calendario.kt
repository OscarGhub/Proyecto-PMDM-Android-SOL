package com.example.fruitask.ui.components

import android.widget.CalendarView
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import java.util.Calendar

@Composable
fun Calendario(
    modifier: Modifier = Modifier,
    onDateSelected: (Long) -> Unit = {}
) {
    AndroidView(
        factory = { context ->
            CalendarView(context).apply {
                setOnDateChangeListener { _, year, month, dayOfMonth ->
                    val calendar = Calendar.getInstance()
                    calendar.set(year, month, dayOfMonth)
                    onDateSelected(calendar.timeInMillis)
                }
            }
        },
        modifier = modifier.fillMaxWidth()
    )
}