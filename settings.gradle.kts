pluginManagement {
    repositories {
        // Eliminamos las restricciones de contenido para permitir que Gradle
        // encuentre todos los plugins y dependencias necesarios.
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Level-Up"
include(":app")
