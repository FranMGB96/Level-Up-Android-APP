package com.example.level_up.ui.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.level_up.ui.screen.CatalogoScreen
import com.example.level_up.ui.screen.CarritoScreen
import com.example.level_up.ui.screen.PerfilScreen
import com.example.level_up.ui.theme.LevelUpTheme
import org.junit.Rule
import org.junit.Test

/**
 * Test de instrumentación para la pantalla de Catálogo (`CatalogoScreen`).
 *
 * Este test utiliza el framework de Jetpack Compose Test para verificar
 * que los componentes de la UI se muestran correctamente en un dispositivo o emulador.
 *
 * El objetivo es asegurar que la UI se renderiza como se espera. Por ejemplo,
 * que el título "CATÁLOGO, MI PERFIL, MI CARRITO" sea visible al mostrar la pantalla.
 */
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun catalogoScreen_muestraElTituloCorrectamente() {
        // Arrange: Preparamos la UI que queremos probar.
        composeTestRule.setContent {
            LevelUpTheme {
                CatalogoScreen(
                    onNavigateToProfile = { },
                    onNavigateToCart = { }
                )
            }
        }

        // Assert: Verificamos que el estado de la UI es el esperado.
        composeTestRule.onNodeWithText("CATÁLOGO").assertIsDisplayed()
    }
    @Test
    fun carritoScreen_muestraElTituloCorrectamente() {
        // Arrange: Preparamos la UI que queremos probar.
        composeTestRule.setContent {
            LevelUpTheme {
                CarritoScreen(
                    onBack = { }
                )
            }
        }

        // Assert: Verificamos que el estado de la UI es el esperado.
        composeTestRule.onNodeWithText("MI CARRITO").assertIsDisplayed()
    }
    @Test
    fun perfilScreen_muestraElTituloCorrectamente() {
        // Arrange: Preparamos la UI que queremos probar.
        composeTestRule.setContent {
            LevelUpTheme {
                PerfilScreen(
                    onBack = { },
                    onLogout = { },
                    onLocation = { },
                    onAdmin = { }
                )
            }
        }

        // Assert: Verificamos que el estado de la UI es el esperado.
        composeTestRule.onNodeWithText("MI PERFIL").assertIsDisplayed()
    }
}
