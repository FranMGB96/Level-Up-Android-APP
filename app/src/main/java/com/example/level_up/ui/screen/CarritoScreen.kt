package com.example.level_up.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.level_up.data.EstadoApp
import com.example.level_up.model.Articulo
import com.example.level_up.ui.components.AppButton
import kotlinx.coroutines.delay
import java.text.NumberFormat
import java.util.Locale

private val priceFormatter: NumberFormat = NumberFormat.getIntegerInstance(Locale.GERMANY)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(onBack: () -> Unit) {
    val cartItems = EstadoApp.carrito
    val totalPrice = cartItems.sumOf { it.producto.precio * it.cantidad }

    var compraRealizada by rememberSaveable { mutableStateOf(false) }
    var showDialog by rememberSaveable { mutableStateOf(false) }

    if (compraRealizada) {
        LaunchedEffect(Unit) {
            delay(3000)
            onBack()
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirmar acción") },
            text = { Text("¿Estás seguro de que quieres vaciar el carrito?") },
            confirmButton = {
                Button(
                    onClick = {
                        EstadoApp.carrito.clear()
                        showDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MI CARRITO", fontWeight = FontWeight.Bold, color = Color(0xFF00FF00)) },
                navigationIcon = {
                    if (!compraRealizada) {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Color(0xFF00FF00))
                        }
                    }
                },
                actions = {
                    if (cartItems.isNotEmpty() && !compraRealizada) {
                        IconButton(onClick = { showDialog = true }) {
                            Icon(Icons.Default.Delete, contentDescription = "Vaciar Carrito", tint = Color.Gray)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        },
        containerColor = Color.Black
    ) { paddingValues ->
        if (compraRealizada) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "¡Gracias por su compra!",
                    color = Color(0xFF00FF00),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)
            ) {
                if (cartItems.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Tu carrito está vacío", color = Color.Gray, fontSize = 20.sp)
                    }
                } else {
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(cartItems) { item ->
                            CarritoItem(
                                item = item,
                                onQuantityChange = { newQuantity ->
                                    val index = cartItems.indexOf(item)
                                    if (newQuantity > 0) {
                                        cartItems[index] = item.copy(cantidad = newQuantity)
                                    } else {
                                        cartItems.removeAt(index)
                                    }
                                },
                                onRemove = {
                                    cartItems.remove(item)
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }

                    Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total:", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Text("$${priceFormatter.format(totalPrice)}", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF00FF00))
                    }

                    Spacer(Modifier.height(24.dp))

                    AppButton(
                        text = "PAGAR",
                        enabled = cartItems.isNotEmpty(),
                        onClick = {
                            compraRealizada = true
                            EstadoApp.carrito.clear()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CarritoItem(
    item: Articulo,
    onQuantityChange: (Int) -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1E))
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = item.producto.imagen),
                contentDescription = item.producto.nombre,
                modifier = Modifier.size(100.dp) // Imagen más grande
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally // Contenido centrado
            ) {
                Text(
                    item.producto.nombre,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { onQuantityChange(item.cantidad - 1) }, modifier = Modifier.size(40.dp)) {
                        Text("-", color = Color(0xFF00FF00), fontSize = 24.sp)
                    }
                    Text(
                        "${item.cantidad}",
                        color = Color.White,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    IconButton(onClick = { onQuantityChange(item.cantidad + 1) }, modifier = Modifier.size(40.dp)) {
                        Text("+", color = Color(0xFF00FF00), fontSize = 24.sp)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "$${priceFormatter.format(item.producto.precio * item.cantidad)}",
                    color = Color(0xFF00FF00),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            IconButton(onClick = onRemove) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Gray)
            }
        }
    }
}
