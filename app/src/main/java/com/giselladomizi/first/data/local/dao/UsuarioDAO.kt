package com.giselladomizi.first.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import com.giselladomizi.first.data.local.entity.Usuario

@Dao
interface UsuarioDAO {
    // Insertar un usuario (devuelve el id autogenerado)
    @Insert
    suspend fun insertUsuario(usuario: Usuario): Long

    // Actualizar un usuario
    @Update
    suspend fun updateUsuario(usuario: Usuario)

    // Eliminar un usuario
    @Delete
    suspend fun deleteUsuario(usuario: Usuario)

    // Traer todos los usuarios y verificar usuario y contraseña
    @Query("SELECT * FROM usuarios WHERE name_u = :username AND passw_u = :password LIMIT 1")
    suspend fun login(username: String, password: String): Usuario?

    //Traigo todos los usuarios y verifico usuario
    @Query("SELECT * FROM usuarios WHERE name_u = :username LIMIT 1")
    suspend fun getUsuarioByName(username: String): Usuario?

}