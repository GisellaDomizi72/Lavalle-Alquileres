package com.giselladomizi.first.data

import androidx.room.Database
import androidx.room.RoomDatabase

// version = 1 porque es la primera vez que la creamos
@Database(
    entities = [Usuario::class, Perfil::class, Alquiler::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    // Room genera la implementación real en compilación
    abstract fun usuarioDAO(): UsuarioDAO
    abstract fun perfilDAO(): PerfilDAO
    abstract fun alquilerDAO(): AlquilerDAO
}