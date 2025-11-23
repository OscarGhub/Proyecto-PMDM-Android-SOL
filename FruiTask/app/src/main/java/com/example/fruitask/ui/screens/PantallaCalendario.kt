package com.example.fruitask.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fruitask.ui.components.Calendario
import com.example.fruitask.ui.theme.VerdeFondo

@Composable
fun PantallaCalendario(
    modifier: Modifier = Modifier,
    onDateSelected: (Long) -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(VerdeFondo),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp)
        ) {

            Text(
                text = "Calendario",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 30.sp
            )

            Spacer(modifier = Modifier.height(30.dp))



            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(0.9f),
                contentAlignment = Alignment.Center
            ) {
                Calendario(
                    modifier = Modifier,
                    onDateSelected = onDateSelected
                )
            }
        }
    }
}