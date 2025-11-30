package com.example.level_up.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.level_up.model.Producto

/**
 * `TarjetaProducto` es un componente de UI reutilizable para mostrar un producto.
 *
 * Es un componente "tonto" (dumb component), lo que significa que no tiene lógica propia.
 * Simplemente recibe un objeto `Producto` y muestra sus datos. Cuando el usuario
 * interactúa con él (ej: pulsando el botón), notifica al componente padre a través
 * de la función lambda `onAgregarClick`.
 *
 * @param producto El objeto `Producto` cuyos datos se van a mostrar.
 * @param onAgregarClick La acción que se ejecuta cuando se pulsa el botón "AGREGAR".
 */
@Composable
fun TarjetaProducto(
    producto: Producto,
    onAgregarClick: (Producto) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .border(2.dp, Color(0xFF00FF00), RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0A0A0A))
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Card(
                modifier = Modifier.size(150.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Image(
                    painter = painterResource(id = producto.imagen),
                    contentDescription = "Imagen de ${producto.nombre}",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        producto.nombre,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00BFFF)
                    )
                    Text(
                        "$${producto.precio.toInt()}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                AppButton(
                    text = "AGREGAR",
                    onClick = { onAgregarClick(producto) }
                )
            }
        }
    }
}
