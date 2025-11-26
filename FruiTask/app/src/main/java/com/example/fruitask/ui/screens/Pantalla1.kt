package com.example.fruitask.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.fruitask.R
import com.example.fruitask.data.model.FruitType
import com.example.fruitask.viewmodel.FruitViewModel

@Composable
fun Pantalla1(
    modifier: Modifier = Modifier,
    viewModel: FruitViewModel
) {
    val scrollState = rememberScrollState()

    var fruitTypeSeleccionado by remember { mutableStateOf<FruitType?>(null) }

    var nombreMostrar by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }
    var nombrePersonaje by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Box( // Contenedor del letrero
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.letrero),
                contentDescription = "Mascota",
                modifier = Modifier.fillMaxSize()
            )
        }

        // --- Componente de selección ---
        FruitSelectionBox(
            drawableId = R.drawable.sandia,
            fruitName = "Sandía",
            onClick = {
                fruitTypeSeleccionado = FruitType.SANDIA
                nombreMostrar = "Sandía"
                nombrePersonaje = ""
                showDialog = true
            }
        )

        // --- Componente de selección (Kiwi) ---
        FruitSelectionBox(
            drawableId = R.drawable.kiwi,
            fruitName = "Kiwi",
            onClick = {
                fruitTypeSeleccionado = FruitType.KIWI
                nombreMostrar = "Kiwi"
                nombrePersonaje = ""
                showDialog = true
            }
        )

        // --- Componente de selección (Manzana) ---
        FruitSelectionBox(
            drawableId = R.drawable.manzana,
            fruitName = "Manzana",
            onClick = {
                fruitTypeSeleccionado = FruitType.MANZANA
                nombreMostrar = "Manzana"
                nombrePersonaje = ""
                showDialog = true
            }
        )

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false; nombrePersonaje = "" },
                title = {
                    Text(
                        style = MaterialTheme.typography.headlineSmall,
                        text = "Seleccionaste: $nombreMostrar",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                text = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            style = MaterialTheme.typography.labelLarge,
                            text = "Asigna un nombre a tu $nombreMostrar")

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = nombrePersonaje,
                            onValueChange = { nombrePersonaje = it },
                            label = { Text("Nombre") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },

                confirmButton = {
                    Button(
                        onClick = {
                            // Guardamos solo si el nombre no está vacío Y hay un tipo de fruta seleccionado
                            if (nombrePersonaje.isNotBlank() && fruitTypeSeleccionado != null) {

                                // Llamada al ViewModel usando el Enum que se guardó al hacer clic
                                viewModel.addFruit(
                                    id = 0,
                                    nombre = nombrePersonaje,
                                    tipo = fruitTypeSeleccionado!!, // Usamos el Enum directamente
                                    nivel = 0,
                                    experiencia = 0.0
                                )
                            }
                            // Cerrar el diálogo y limpiar el estado
                            showDialog = false
                            nombrePersonaje = ""
                        },
                        // Habilitar el botón solo si hay nombre y tipo seleccionado
                        enabled = nombrePersonaje.isNotBlank() && fruitTypeSeleccionado != null,
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        Text(
                            "Guardar y Continuar",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false; nombrePersonaje = "" }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

// Componente reutilizable para las cajas de selección
@Composable
fun FruitSelectionBox(drawableId: Int, fruitName: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .height(160.dp)
            .padding(vertical = 4.dp, horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = drawableId),
            contentDescription = fruitName,
            modifier = Modifier.fillMaxSize()
        )
        Text(
            text = fruitName,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.align(Alignment.BottomStart).padding(8.dp)
        )
    }
}