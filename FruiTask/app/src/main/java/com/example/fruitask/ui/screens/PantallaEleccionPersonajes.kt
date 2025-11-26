package com.example.fruitask.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.example.fruitask.ui.theme.VerdeFondo

@Composable
fun PantallaEleccionPersonajes(modifier: Modifier = Modifier, onFrutaSeleccionada: (tipo: String, nombre: String) -> Unit) {

    val scrollState = rememberScrollState()
    var personajeSeleccionado by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var nombreFruta by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(VerdeFondo)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Box(
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    personajeSeleccionado = "Sandia"
                    showDialog = true
                }
                .height(160.dp),

            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.sandia),
                contentDescription = "Mascota",
                modifier = Modifier.fillMaxSize()
            )
        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    personajeSeleccionado = "Kiwi"
                    showDialog = true
                }
                .height(160.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.kiwi),
                contentDescription = "Mascota",
                modifier = Modifier.fillMaxSize()
            )
        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    personajeSeleccionado = "Manzana"
                    showDialog = true
                }
                .height(160.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.manzana),
                contentDescription = "Mascota",
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    style = MaterialTheme.typography.headlineLarge,
                    text = "Personaje seleccionado",
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
                        text = "Asigna un nombre a tu $personajeSeleccionado")

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = nombreFruta,
                        onValueChange = { nombreFruta = it },
                        label = { Text("Nombre") },
                        modifier = Modifier.fillMaxWidth(0.9f).padding(start = 30.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        // LLAMADA FINAL: Pasa el tipo y el nombre de la fruta
                        onFrutaSeleccionada(personajeSeleccionado, nombreFruta)
                    },
                    enabled = nombreFruta.isNotBlank(),
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    Text(
                        "OK",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        )
    }
}