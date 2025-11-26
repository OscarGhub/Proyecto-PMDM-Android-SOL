package com.example.fruitask.ui.screens

import Pantalla3
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.fruitask.ui.theme.VerdeBoton
import com.example.fruitask.ui.theme.VerdeFondo

@Composable
fun PantallaPrincipal() {

    var selectedTab by remember { mutableStateOf(0) }

    val tabs = listOf(
        Pair("Home", Icons.Filled.Home),
        Pair("Calendario", Icons.Filled.CalendarToday),
        Pair("Horas", Icons.Filled.AccessTime)
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                tabs.forEachIndexed { index, tab ->
                    NavigationBarItem(
                        icon = { Icon(tab.second, contentDescription = tab.first) },
                        label = { Text(tab.first) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index }
                    )
                }
            }
        }
    ) { innerPadding ->
        when (selectedTab) {
            0 -> Pantalla1(Modifier.padding(innerPadding))
            1 -> Pantalla2(Modifier.padding(innerPadding))
            2 -> Pantalla3(Modifier.padding(innerPadding))
        }
    }
}