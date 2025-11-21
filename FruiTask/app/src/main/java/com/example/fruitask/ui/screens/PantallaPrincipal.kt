package com.example.fruitask.ui.screens

import Pantalla4
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun PantallaPrincipal() {
    var selectedTab by remember { mutableIntStateOf(0) }

    // Lista de pestañas con íconos
    val tabs = listOf(
        Pair("Pantalla 1", Icons.Filled.Home),
        Pair("Pantalla 2", Icons.Filled.Favorite),
        Pair("Pantalla 3", Icons.Filled.Settings),
        Pair("Pantalla 4", Icons.Filled.Home) // Puedes cambiar iconos
    )

    Scaffold(
        bottomBar = {
            NavigationBar { // material3 NavigationBar en lugar de BottomNavigation
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
            3 -> Pantalla4(Modifier.padding(innerPadding))
        }
    }
}