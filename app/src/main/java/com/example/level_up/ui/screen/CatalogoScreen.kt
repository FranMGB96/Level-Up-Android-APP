package com.example.level_up.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.level_up.data.EstadoApp
import com.example.level_up.model.game.GameDeal
import com.example.level_up.ui.components.TarjetaProducto
import com.example.level_up.viewmodel.CatalogoViewModel
import com.example.level_up.viewmodel.GameViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen(
    onNavigateToProfile: () -> Unit,
    onNavigateToCart: () -> Unit
) {
    val catalogoViewModel: CatalogoViewModel = viewModel()
    val gameViewModel: GameViewModel = viewModel()

    val productos by catalogoViewModel.productos.collectAsState()
    val carrito = EstadoApp.carrito

    val giveaways by gameViewModel.giveaways.collectAsState()
    val isLoading by gameViewModel.isLoading.collectAsState()

    var showGames by remember { mutableStateOf(false) }

    LaunchedEffect(showGames) {
        if (showGames && giveaways.isEmpty()) {
            gameViewModel.fetchGiveaways()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    val titleText = if (showGames) "NOTICIAS" else "CATÁLOGO"
                    Text(titleText, fontWeight = FontWeight.Bold, color = Color(0xFF00FF00))
                },
                actions = {
                    TextButton(onClick = { showGames = !showGames }) {
                        Text(if (showGames) "Productos" else "Videojuegos", color = Color(0xFF00FF00))
                    }
                    IconButton(onClick = onNavigateToCart) {
                        BadgedBox(badge = {
                            if (carrito.isNotEmpty()) {
                                val totalItems = carrito.sumOf { it.cantidad }
                                Badge(containerColor = Color.Red) {
                                    Text("$totalItems")
                                }
                            }
                        }) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito", tint = Color(0xFF00FF00))
                        }
                    }
                    Spacer(Modifier.width(8.dp))
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(Icons.Default.Person, contentDescription = "Perfil", tint = Color(0xFF00FF00))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        },
        containerColor = Color.Black
    ) { padding ->
        if (showGames) {
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF00FF00))
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)
                ) {
                    Text(
                        text = "Novedades sobre Juegos Gratuitos o Boletín de Ofertas para Gamers",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00FF00),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(giveaways) { deal ->
                            GameDealCard(deal = deal)
                        }
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(productos) { producto ->
                    TarjetaProducto(
                        producto = producto,
                        onAgregarClick = { catalogoViewModel.agregarAlCarrito(it) }
                    )
                }
            }
        }
    }
}

@Composable
fun GameDealCard(deal: GameDeal) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color(0xFF00FF00), RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0A0A0A))
    ) {
        Column {
            AsyncImage(
                model = deal.image,
                contentDescription = "Imagen del juego",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = deal.title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = deal.description, style = MaterialTheme.typography.bodyMedium, color = Color.LightGray)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Plataformas: ${deal.platforms}", style = MaterialTheme.typography.bodySmall, color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Valor: ${deal.worth}", style = MaterialTheme.typography.bodySmall, color = Color.White)
            }
        }
    }
}
