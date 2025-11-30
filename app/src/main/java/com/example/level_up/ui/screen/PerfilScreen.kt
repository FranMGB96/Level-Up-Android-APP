package com.example.level_up.ui.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.level_up.data.EstadoApp
import com.example.level_up.ui.components.AppButton
import com.example.level_up.viewmodel.PerfilViewModel
import com.example.level_up.viewmodel.SelectorImagenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    onBack: () -> Unit,
    onLogout: () -> Unit,
    onLocation: () -> Unit,
    onAdmin: () -> Unit, // Parámetro para navegar al panel de admin
    imagenViewModel: SelectorImagenViewModel = viewModel(),
    perfilViewModel: PerfilViewModel = viewModel()
) {
    val user by EstadoApp.usuarioActual

    LaunchedEffect(user) {
        user?.let { perfilViewModel.onNombreChange(it.nombre) }
    }

    val lanzadorGaleria = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            imagenViewModel.asignarUriImagen(uri?.toString())
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MI PERFIL", fontWeight = FontWeight.Bold, color = Color(0xFF00FF00)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Color(0xFF00FF00))
                    }
                },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Cerrar Sesión", tint = Color.Gray)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        },
        containerColor = Color.Black
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, Color(0xFF00FF00), RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0A0A0A))
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF0A0A0A))
                            .border(2.dp, Color(0xFF00FF00), CircleShape)
                            .clickable { lanzadorGaleria.launch("image/*") },
                        contentAlignment = Alignment.Center
                    ) {
                        if (imagenViewModel.uriImagen != null) {
                            AsyncImage(
                                model = imagenViewModel.uriImagen,
                                contentDescription = "Imagen de perfil seleccionada",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Text(
                                user?.nombre?.first()?.uppercase() ?: "U",
                                fontSize = 50.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF00FF00)
                            )
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                    Text(user?.email ?: "", fontSize = 16.sp, color = Color.Gray)
                    Spacer(Modifier.height(24.dp))

                    OutlinedTextField(
                        value = perfilViewModel.nombre,
                        onValueChange = { perfilViewModel.onNombreChange(it) },
                        label = { Text("Nombre de Usuario") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF00FF00),
                            unfocusedBorderColor = Color.Gray,
                            focusedLabelColor = Color(0xFF00FF00),
                            cursorColor = Color(0xFF00FF00),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            unfocusedLabelColor = Color.Gray
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(16.dp))

                    AppButton(
                        text = "GUARDAR CAMBIOS",
                        onClick = { perfilViewModel.guardarCambios() },
                        enabled = perfilViewModel.nombre.isNotBlank() && perfilViewModel.nombre != user?.nombre
                    )

                    AnimatedVisibility(visible = perfilViewModel.guardadoConExito) {
                        Text(
                            "¡Nombre guardado con éxito!",
                            color = Color(0xFF00FF00),
                            modifier = Modifier.padding(top = 12.dp)
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Botón condicional para el panel de administrador
            if (user?.isAdmin == true) {
                Button(onClick = onAdmin) {
                    Icon(Icons.Default.AdminPanelSettings, contentDescription = "Panel de Administrador")
                    Spacer(Modifier.width(8.dp))
                    Text("PANEL DE ADMINISTRADOR")
                }
                Spacer(Modifier.height(8.dp))
            }

            Button(onClick = onLocation) {
                Icon(Icons.Default.LocationOn, contentDescription = "Icono de ubicación")
                Spacer(Modifier.width(8.dp))
                Text("VER UBICACIÓN")
            }

            Spacer(Modifier.weight(1f))

            AppButton(
                text = "CERRAR SESIÓN",
                onClick = onLogout,
                color = Color.Red
            )
        }
    }
}
