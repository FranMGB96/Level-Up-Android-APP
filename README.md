[README.md](https://github.com/user-attachments/files/32630263/README.md)
# 🎮 Level Up

App de e-commerce para Android enfocada en productos gamer (consolas, PC gamer, sillas gamer y accesorios), desarrollada de forma nativa con **Kotlin** y **Jetpack Compose**.

## 📱 Descripción

Level Up simula una tienda online de artículos gamer donde el usuario puede registrarse, iniciar sesión, explorar un catálogo de productos, agregarlos a un carrito de compras y gestionar su perfil. Además, integra una **API REST externa** para mostrar ofertas y giveaways de videojuegos en tiempo real.

## ✨ Funcionalidades

- **Autenticación de usuarios**: registro e inicio de sesión con persistencia en base de datos local
- **Sesión persistente**: la sesión del usuario se mantiene aunque se cierre la app (`SharedPreferences`)
- **Catálogo de productos**: listado de consolas, PC gamer, sillas y accesorios con imágenes y precios
- **Carrito de compras**: agregar y gestionar artículos antes de la compra
- **Perfil de usuario**: visualización y gestión de datos de la cuenta
- **Geolocalización**: pantalla de ubicación con permisos de GPS gestionados en tiempo real
- **Ofertas de videojuegos en vivo**: integración con la API de [GamerPower](https://www.gamerpower.com/) vía Retrofit para mostrar giveaways actualizados
- **Panel de administración**: pantalla y lógica dedicada para gestión de productos

## 🛠️ Stack técnico

| Categoría | Tecnología |
|---|---|
| Lenguaje | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Arquitectura | MVVM + Repository Pattern |
| Base de datos local | Room |
| Persistencia de sesión | SharedPreferences |
| Navegación | Navigation Compose |
| Networking | Retrofit + Gson |
| Carga de imágenes | Coil |
| Ubicación | Google Play Services Location + Accompanist Permissions |
| Concurrencia | Kotlin Coroutines |
| Testing | JUnit (unitario) + Espresso (instrumentado) |

## 🏗️ Arquitectura

El proyecto sigue una arquitectura por capas inspirada en MVVM:

```
com.example.level_up
├── data/           # DAOs, base de datos Room, gestión de sesión
├── model/          # Entidades de datos (Usuario, Producto, Articulo, GameDeal)
├── remote/         # Cliente y servicio Retrofit para la API externa
├── repository/     # Intermediarios entre ViewModels y fuentes de datos
├── viewmodel/      # Lógica de presentación de cada pantalla
├── ui/screen/      # Pantallas Compose (Login, Catálogo, Carrito, Perfil, etc.)
├── ui/components/  # Componentes reutilizables (botones, campos de texto, tarjetas)
├── ui/theme/       # Tema visual de la app (colores, tipografía)
└── navigation/     # Grafo de navegación de la app
```

## 🚀 Cómo ejecutar el proyecto

1. Clonar el repositorio
   ```bash
   git clone https://github.com/tu-usuario/level-up-android.git
   ```
2. Abrir el proyecto en **Android Studio** (versión reciente recomendada)
3. Sincronizar con Gradle
4. Ejecutar en un emulador o dispositivo físico con Android 7.0 (API 24) o superior

## 🧪 Tests

El proyecto incluye tests unitarios e instrumentados:

```bash
./gradlew test               # Tests unitarios (JVM)
./gradlew connectedAndroidTest # Tests instrumentados (requiere emulador/dispositivo)
```

## 📌 Estado del proyecto

Proyecto desarrollado con fines académicos como parte de la carrera de Ingeniería en Informática. Próximas mejoras contempladas:

- [ ] Hash de contraseñas (actualmente almacenadas en texto plano en la base de datos local)
- [ ] Manejo de errores más robusto en las llamadas a la API externa
- [ ] Persistencia del carrito de compras

## 👤 Autor

Desarrollado por Franci como parte de su formación en Ingeniería en Informática, mención en Desarrollo de Software.
