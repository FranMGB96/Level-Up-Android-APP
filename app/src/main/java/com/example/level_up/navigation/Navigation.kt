package com.example.level_up.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.level_up.data.SessionManager
import com.example.level_up.ui.screen.*

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController, startDestination = "pantalla") {
        composable("pantalla") {
            PantallaScreen(
                onNavigateToLogin = { 
                    navController.navigate("login") { 
                        popUpTo("pantalla") { inclusive = true } 
                    }
                },
                onNavigateToCatalog = { 
                    navController.navigate("catalogo") { 
                        popUpTo("pantalla") { inclusive = true } 
                    }
                }
            )
        }
        composable("login") {
            LoginScreen(
                onNavigate = { navController.navigate("catalogo") { popUpTo("login") { inclusive = true } } },
                onRegister = { navController.navigate("registrar") }
            )
        }
        composable("registrar") {
            RegistrarScreen(
                onSuccess = { navController.navigate("catalogo") { popUpTo("registrar") { inclusive = true } } },
                onBack = { navController.popBackStack() }
            )
        }
        composable("catalogo") {
            CatalogoScreen(
                onNavigateToProfile = { navController.navigate("perfil") },
                onNavigateToCart = { navController.navigate("carrito") }
            )
        }
        composable("perfil") {
            PerfilScreen(
                onBack = { navController.popBackStack() },
                onLogout = { 
                    // Limpiar la sesión guardada antes de navegar al login
                    SessionManager.clearSession(context)
                    navController.navigate("login") { 
                        popUpTo("catalogo") { inclusive = true } 
                    }
                },
                onLocation = { navController.navigate("ubicacion") },
                onAdmin = { navController.navigate("admin_screen") }
            )
        }
        composable("carrito") {
            CarritoScreen(onBack = { navController.popBackStack() })
        }
        composable("ubicacion") {
            UbicacionScreen(onBack = { navController.popBackStack() })
        }
        composable("admin_screen") {
            AdminScreen(onBack = { navController.popBackStack() })
        }
    }
}
