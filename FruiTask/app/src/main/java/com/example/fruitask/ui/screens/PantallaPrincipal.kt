package com.example.fruitask.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.unit.dp

@Composable
fun PantallaPrincipal() {
    var selectedTab by remember { mutableIntStateOf(0) }

    val tabs = listOf(
        Pair("Pantalla 1", Icons.Filled.Home),
        Pair("Pantalla 2", Icons.Filled.Favorite),
        Pair("Pantalla 3", Icons.Filled.Settings),
        Pair("Pantalla 4", Icons.Filled.Home)
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
            3 -> Pantalla4(Modifier.padding(innerPadding))
        }
    }
}
