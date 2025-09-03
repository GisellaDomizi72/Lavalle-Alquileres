package com.giselladomizi.first.data

import androidx.room.*

@Dao
interface PerfilDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerfil(perfil: Perfil)

    @Update
    suspend fun updatePerfil(perfil: Perfil)

    @Delete
    suspend fun deletePerfil(perfil: Perfil)

    //@Query("SELECT * FROM perfil WHERE id_perfil = :id")
    //suspend fun getPerfilById(id: Int): Perfil?

    //@Query("SELECT * FROM perfil WHERE id_user = :userId")
    //suspend fun getPerfilesByUser(userId: Int): List<Perfil>

    //@Query("SELECT * FROM perfil")
    //suspend fun getAllPerfiles(): List<Perfil>
}