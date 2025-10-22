package com.giselladomizi.first.data.local.databese

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.giselladomizi.first.data.local.dao.AlquilerDAO
import com.giselladomizi.first.data.local.dao.PerfilDAO
import com.giselladomizi.first.data.local.dao.UsuarioDAO
import com.giselladomizi.first.data.local.entity.Alquiler
import com.giselladomizi.first.data.local.entity.Perfil
import com.giselladomizi.first.data.local.entity.Usuario

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
    // se declara una instancia de la BD como singleton
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // crea la instancia de la BD si no existe
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bd_lavalle_alquileres"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}