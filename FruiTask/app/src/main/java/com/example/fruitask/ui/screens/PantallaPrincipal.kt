package com.example.fruitask.ui.screens

import Pantalla3
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Home
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
import com.example.fruitask.data.local.database.MyViewModel
import com.example.fruitask.ui.theme.VerdeFondo

@Composable
fun PantallaPrincipal(viewModel: MyViewModel) {

    var selectedTab by remember { mutableIntStateOf(0) }

    val tabs = listOf(
        Pair("Home", Icons.Filled.Home),
        Pair("Calendario", Icons.Filled.CalendarToday),
        Pair("Horas", Icons.Filled.AccessTime)
    )

    Scaffold(
        containerColor = VerdeFondo,
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
            0 -> Pantalla1(Modifier.padding(innerPadding), viewModel)
            1 -> Pantalla2(Modifier.padding(innerPadding), viewModel)
            2 -> Pantalla3(Modifier.padding(innerPadding), viewModel)
        }
    }
}