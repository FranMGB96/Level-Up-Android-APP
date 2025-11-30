package com.example.level_up.viewmodel

import android.app.Application
import com.example.level_up.data.AppDatabase
import com.example.level_up.data.ProductoDao
import com.example.level_up.model.Producto
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Test unitario para [CatalogoViewModel].
 *
 * Este test verifica que la lógica del ViewModel funciona correctamente de forma aislada.
 * Para ello, utiliza MockK para simular (`mockear`) la dependencia de [ProductoDao],
 * evitando así la necesidad de acceder a la base de datos real.
 *
 * El objetivo es asegurar que el `StateFlow` de `productos` en el ViewModel
 * expone correctamente los datos que recibe desde el DAO.
 */
@ExperimentalCoroutinesApi
class CatalogoViewModelTest {

    // Dispatcher para controlar las corutinas en los tests
    private val testDispatcher = StandardTestDispatcher()

    // Mocks para las dependencias
    private lateinit var mockApplication: Application
    private lateinit var mockProductoDao: ProductoDao

    // La clase que estamos probando
    private lateinit var viewModel: CatalogoViewModel

    @BeforeEach
    fun setUp() {
        // Establecemos el dispatcher principal para los tests
        Dispatchers.setMain(testDispatcher)

        // Creamos los mocks
        mockApplication = mockk()
        mockProductoDao = mockk()

        // Mockeamos el companion object de la base de datos para inyectar nuestro DAO falso
        mockkObject(AppDatabase)
        every { AppDatabase.getDatabase(mockApplication).productoDao() } returns mockProductoDao
    }

    @Test
    fun `cuando el dao emite productos, el viewmodel los expone`() = runTest {
        // Given: Preparamos el comportamiento de nuestro mock DAO
        val listaDeProductosFalsa = listOf(
            Producto(id = 1, nombre = "Laptop Gamer", precio = 1500.0, imagen = 0),
            Producto(id = 2, nombre = "Teclado Mecánico", precio = 120.0, imagen = 0)
        )
        every { mockProductoDao.obtenerTodos() } returns flowOf(listaDeProductosFalsa)

        // When: Creamos la instancia del ViewModel
        viewModel = CatalogoViewModel(mockApplication)

        // Then: Verificamos que el StateFlow del ViewModel emite la lista falsa
        // Usamos .drop(1) para saltarnos el valor inicial (emptyList) del StateFlow
        val resultado = viewModel.productos.drop(1).first()
        assertEquals(listaDeProductosFalsa, resultado)
    }

    @AfterEach
    fun tearDown() {
        // Limpiamos y reseteamos el dispatcher principal
        Dispatchers.resetMain()
    }
}
