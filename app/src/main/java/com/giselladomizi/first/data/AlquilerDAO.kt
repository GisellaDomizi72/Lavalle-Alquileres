package com.giselladomizi.first.data

import androidx.room.*
interface AlquilerDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlquiler(alquiler: Alquiler)

    @Update
    suspend fun updateAlquiler(alquiler: Alquiler)

    @Delete
    suspend fun deleteAlquiler(alquiler: Alquiler)

    //@Query("SELECT * FROM alquiler WHERE id_alqui = :id")
    //suspend fun getAlquilerById(id: Int): Alquiler?

    //@Query("SELECT * FROM alquiler WHERE id_perfil = :perfilId")
    //suspend fun getAlquileresByPerfil(perfilId: Int): List<Alquiler>

    //@Query("SELECT * FROM alquiler")
    //suspend fun getAllAlquileres(): List<Alquiler>
}