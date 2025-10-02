package com.giselladomizi.first.data.local.dao

import androidx.room.*
import com.giselladomizi.first.data.local.entity.Alquiler
@Dao
interface AlquilerDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlquiler(alquiler: Alquiler)
//Update - Actualizar alquiler
    @Update
    suspend fun updateAlquiler(alquiler: Alquiler)

    @Query("SELECT * FROM alquiler WHERE id_alqui = :id")
    suspend fun getAlquilerById(id: Int): Alquiler?


//Eliminar un alquiler
    @Delete
    suspend fun  deleteAlquiler(alquiler: Alquiler)

    @Query("SELECT * FROM alquiler")
    suspend fun getAllAlquileres(): List<Alquiler>

    //Consulta para buscar alquiler segun el id del perfil logueado
    @Query("SELECT * FROM alquiler WHERE id_perfil = :perfilId")
    suspend fun getAlquileresByPerfil(perfilId: Int): List<Alquiler>

    //Consulta que devuelve alquileres donde tipo o ubicacion contengan el texto ingresado
    @Query("SELECT * FROM alquiler WHERE tipo_alqui LIKE '%' || :query || '%' OR ubicacion_alqui LIKE '%' || :query || '%'")
    suspend fun searchAlquileres(query: String): List<Alquiler>

}