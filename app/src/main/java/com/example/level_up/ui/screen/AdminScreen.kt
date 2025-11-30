package com.example.level_up.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.level_up.model.Producto
import com.example.level_up.viewmodel.AdminViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    onBack: () -> Unit,
    adminViewModel: AdminViewModel = viewModel()
) {
    val productos by adminViewModel.productos.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PANEL DE ADMINISTRADOR", fontWeight = FontWeight.Bold, color = Color(0xFF00FF00)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Color(0xFF00FF00))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        },
        containerColor = Color.Black
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            items(productos, key = { it.id }) { producto -> // Usar una key para mejor rendimiento
                AdminProductoItem(producto = producto, onGuardar = { adminViewModel.guardarCambiosProducto(it) })
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun AdminProductoItem(
    producto: Producto,
    onGuardar: (Producto) -> Unit
) {
    // Guardamos el estado inicial para poder compararlo
    val nombreInicial = remember(producto) { producto.nombre }
    val precioInicial = remember(producto) { producto.precio.toLong().toString() }

    // Estado local para los campos de texto
    var nombre by remember(producto) { mutableStateOf(nombreInicial) }
    var precio by remember(producto) { mutableStateOf(precioInicial) }

    var error by remember { mutableStateOf<String?>(null) }
    var guardado by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1E))
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = producto.imagen),
                contentDescription = nombre,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF00FF00),
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = Color(0xFF00FF00)
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = precio,
                    onValueChange = { precio = it },
                    label = { Text("Precio") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF00FF00),
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = Color(0xFF00FF00)
                    )
                )

                AnimatedVisibility(visible = guardado) {
                    Text("¡Guardado!", color = Color(0xFF00FF00), modifier = Modifier.padding(top = 4.dp))
                }

                AnimatedVisibility(visible = error != null) {
                    Text(error ?: "", color = Color.Red, modifier = Modifier.padding(top = 4.dp))
                }

                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        // Filtra todo lo que no sea un número para convertirlo
                        val precioDouble = precio.filter { it.isDigit() }.toDoubleOrNull()

                        if (nombre.isBlank()) {
                            error = "El nombre no puede estar vacío."
                            return@Button
                        }
                        if (precioDouble == null || precioDouble <= 0) {
                            error = "El precio introducido no es válido."
                            return@Button
                        }

                        error = null
                        val productoActualizado = producto.copy(nombre = nombre.trim(), precio = precioDouble)
                        onGuardar(productoActualizado)

                        scope.launch {
                            guardado = true
                            delay(2000)
                            guardado = false
                        }
                    },
                    // Se activa si el texto en los campos es diferente al original
                    enabled = nombre != nombreInicial || precio != precioInicial,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Guardar")
                }
            }
        }
    }
}
