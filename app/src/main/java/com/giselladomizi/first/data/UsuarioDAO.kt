package com.giselladomizi.first.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update

@Dao
interface UsuarioDAO {
    // Insertar un usuario (devuelve el id autogenerado)
    @Insert
    suspend fun insert(usuario: Usuario): Long

    // Actualizar un usuario
    @Update
    suspend fun update(usuario: Usuario)

    // Eliminar un usuario
    @Delete
    suspend fun delete(usuario: Usuario)

    // Traer todos los usuarios
    //@Query("SELECT * FROM usuarios")
    //suspend fun getAll(): List<Usuario>

    // Buscar usuario por id
    //@Query("SELECT * FROM usuarios WHERE id_user = :id LIMIT 1")
    //suspend fun getById(id: Int): Usuario?

    // Buscar usuario por nombre
    //@Query("SELECT * FROM usuarios WHERE name_u = :nombre LIMIT 1")
    //suspend fun getByName(nombre: String): Usuario?

}