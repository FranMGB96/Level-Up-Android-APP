package com.example.level_up.ui.screen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.level_up.ui.components.AppButton
import com.example.level_up.viewmodel.UbicacionViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.location.LocationServices

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("MissingPermission")
@Composable
fun UbicacionScreen(
    onBack: () -> Unit, // Parámetro para volver atrás
    viewModel: UbicacionViewModel = viewModel()
) {
    val contexto = LocalContext.current

    val permisoUbicacion = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    // Si el permiso se concede, obtiene la ubicación
    if (permisoUbicacion.status.isGranted) {
        LaunchedEffect(Unit) {
            obtenerUbicacion(contexto, viewModel)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MI UBICACIÓN", fontWeight = FontWeight.Bold, color = Color(0xFF00FF00)) },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (permisoUbicacion.status.isGranted) {
                // Vista cuando se tienen permisos
                Text("¡Permiso concedido!", color = Color(0xFF00FF00), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(24.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1E))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Latitud: ${viewModel.latitud ?: "Obteniendo..."}", color = Color.White, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Longitud: ${viewModel.longitud ?: "Obteniendo..."}", color = Color.White, fontSize = 18.sp)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                AppButton(
                    text = "Actualizar Ubicación", 
                    onClick = { obtenerUbicacion(contexto, viewModel) }
                )

            } else {
                // Vista cuando no se tienen permisos
                val textoExplicativo = if (permisoUbicacion.status.shouldShowRationale) {
                    "La ubicación es necesaria para mostrarte tu posición. Por favor, concede el permiso."
                } else {
                    "Para usar esta función, necesitamos acceder a tu ubicación. Activa el permiso en los ajustes de la aplicación."
                }

                Text(
                    text = textoExplicativo,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))

                AppButton(
                    text = "Solicitar Permiso",
                    onClick = {
                        if (permisoUbicacion.status.shouldShowRationale || !permisoUbicacion.status.isGranted) {
                            permisoUbicacion.launchPermissionRequest()
                        } else {
                            // Abre los ajustes de la app si el permiso fue denegado permanentemente
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            intent.data = Uri.fromParts("package", contexto.packageName, null)
                            contexto.startActivity(intent)
                        }
                    }
                )
            }
        }
    }
}

@SuppressLint("MissingPermission")
fun obtenerUbicacion(contexto: Context, viewModel: UbicacionViewModel) {
    val proveedorUbicacion = LocationServices.getFusedLocationProviderClient(contexto)
    proveedorUbicacion.lastLocation.addOnSuccessListener { ubicacion ->
        if (ubicacion != null) {
            viewModel.actualizarUbicacion(ubicacion.latitude, ubicacion.longitude)
        }
    }
}
