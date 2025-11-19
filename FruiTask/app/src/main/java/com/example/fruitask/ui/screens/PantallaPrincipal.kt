package com.example.fruitask.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.nio.file.WatchEvent

@Composable
fun PantallaPrincipal() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Pantalla 1", "Pantalla 2", "Pantalla 3", "Pantalla 4")

    Scaffold(
        topBar = {
            TabRow(selectedTabIndex = selectedTab,
                modifier = Modifier.padding(top = 106.dp)

            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
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