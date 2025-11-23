package com.example.fruitask.ui.screens

import Pantalla4
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.fruitask.ui.theme.VerdeBoton
import com.example.fruitask.ui.theme.VerdeFondo

@Composable
fun PantallaPrincipal() {
    var selectedTab by remember { mutableIntStateOf(0) }

    // Lista de pestañas con íconos
    val tabs = listOf(
        Pair("", Icons.Filled.Home),
        Pair("", Icons.Filled.CalendarToday),
        Pair("", Icons.Filled.AccessTime),
        Pair("", Icons.Filled.Home)
    )

    Scaffold(
        containerColor = VerdeFondo,
        bottomBar = {
            NavigationBar(
                containerColor = VerdeBoton,
                contentColor = Color.White 
            ) {
                tabs.forEachIndexed { index, tab ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                tab.second,
                                contentDescription = tab.first,
                                tint = if (selectedTab == index) Color.White else Color.White.copy(alpha = 0.7f)
                            )
                        },
                        label = {
                            Text(
                                tab.first,
                                color = if (selectedTab == index) Color.White else Color.White.copy(alpha = 0.8f)
                            )
                        },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            selectedTextColor = Color.White,
                            indicatorColor = VerdeBoton,
                            unselectedIconColor = Color.White.copy(alpha = 0.6f),
                            unselectedTextColor = Color.White.copy(alpha = 0.6f)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        when (selectedTab) {
            0 -> Pantalla1(Modifier.padding(innerPadding))
            1 -> PantallaCalendario(Modifier.padding(innerPadding))
            2 -> Pantalla4(Modifier.padding(innerPadding))
            3 -> Pantalla4(Modifier.padding(innerPadding))
        }
    }
}