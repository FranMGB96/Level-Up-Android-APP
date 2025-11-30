package com.example.level_up.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.level_up.R
import com.example.level_up.model.Producto
import com.example.level_up.model.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Define la base de datos de la aplicación usando Room.
 * - Configuración principal de la base de datos que gestiona las tablas `Usuario` y `Producto`.
 * - Utiliza un patrón singleton para asegurar una única instancia de la base de datos.
 * - Al crearse por primera vez, la base de datos se llena con datos iniciales (productos y usuarios).
 * - Usa una migración destructiva: al cambiar el esquema, los datos anteriores se pierden.
 */
@Database(entities = [Usuario::class, Producto::class], version = 5, exportSchema = false) // Versión incrementada
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun productoDao(): ProductoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "level_up_database"
                )
                    .fallbackToDestructiveMigration()
                    // Callback corregido para evitar condiciones de carrera
                    .addCallback(AppDatabaseCallback(context))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    // La lógica de la creación se encapsula en una clase privada para más seguridad
    private class AppDatabaseCallback(private val context: Context) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            CoroutineScope(Dispatchers.IO).launch {
                // Obtenemos la instancia de la base de datos de forma segura aquí dentro
                val database = getDatabase(context)
                insertarDatosPorDefecto(database)
            }
        }

        suspend fun insertarDatosPorDefecto(db: AppDatabase) {
            val productoDao = db.productoDao()
            val productosIniciales = listOf(
                Producto(nombre = "Xbox Series X", precio = 499990.0, imagen = R.drawable.xbox),
                Producto(nombre = "Playstation 5", precio = 449990.0, imagen = R.drawable.playstation),
                Producto(nombre = "PC Gamer Pro", precio = 999990.0, imagen = R.drawable.pcgamer),
                Producto(nombre = "Nintendo Switch OLED", precio = 349990.0, imagen = R.drawable.nintendo_switch),
                Producto(nombre = "Auriculares Gamer RGB", precio = 89990.0, imagen = R.drawable.auriculares_gamer),
                Producto(nombre = "Silla Gamer Ergonómica", precio = 149990.0, imagen = R.drawable.silla_gamer),
                Producto(nombre = "Microfono Hyperx", precio = 89990.0, imagen = R.drawable.microfono),
                Producto(nombre = "Teclado Logitech", precio = 99990.0, imagen = R.drawable.teclado),
                Producto(nombre = "Mouse Logitech", precio = 159990.0, imagen = R.drawable.mouse),
                Producto(nombre = "Monitor Gamer", precio = 189990.0, imagen = R.drawable.monitor),
                Producto(nombre = "PC Gamer Económico", precio = 550000.0, imagen = R.drawable.pc_economico),
                Producto(nombre = "Xbox Series S", precio = 299990.0, imagen = R.drawable.xboxs),
                Producto(nombre = "Playstation 5 Digital", precio = 399990.0, imagen = R.drawable.playstation_digital),
                Producto(nombre = "Nintendo Switch Lite", precio = 199990.0, imagen = R.drawable.lite),
                Producto(nombre = "Auriculares Inalámbricos", precio = 129990.0, imagen = R.drawable.auriculares_inalambricos),
                Producto(nombre = "Silla Gamer Pro", precio = 249990.0, imagen = R.drawable.pro_silla),
                Producto(nombre = "PC Gamer Ultra", precio = 1599990.0, imagen = R.drawable.pc_ultra),
                Producto(nombre = "Control Inalámbrico Xbox", precio = 59990.0, imagen = R.drawable.control_xbox),
                Producto(nombre = "Control DualSense PS5", precio = 64990.0, imagen = R.drawable.dualsense),
                Producto(nombre = "Joy-Con (L/R) para Switch", precio = 79990.0, imagen = R.drawable.joy),
                Producto(nombre = "Auriculares con Micrófono", precio = 49990.0, imagen = R.drawable.audifono_microfono),
                Producto(nombre = "Silla de Oficina", precio = 99990.0, imagen = R.drawable.silla_oficina)
            )
            productoDao.insertarTodos(productosIniciales)

            val usuarioDao = db.usuarioDao()
            val usuarios = listOf(
                Usuario(nombre = "Admin", email = "admin@levelup.com", password = "admin", isAdmin = true),
                Usuario(nombre = "franci", email = "francis@gmail.com", password = "francis"),
                Usuario(nombre = "lucas", email = "lucas@gmail.com", password = "lucass")
            )
            usuarios.forEach { usuarioDao.insertar(it) }
        }
    }
}
