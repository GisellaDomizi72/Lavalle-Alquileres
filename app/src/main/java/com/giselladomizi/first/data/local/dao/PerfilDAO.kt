package com.giselladomizi.first.data.local.dao

import androidx.room.*
import com.giselladomizi.first.data.local.entity.Perfil

@Dao
interface PerfilDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerfil(perfil: Perfil)

    @Update
    suspend fun updatePerfil(perfil: Perfil)

    @Delete
    suspend fun deletePerfil(perfil: Perfil)

    @Query("SELECT * FROM perfil WHERE id_perfil = :id LIMIT 1")
    suspend fun getPerfilById(id: Int): Perfil?

    @Query("SELECT * FROM perfil WHERE id_user = :id LIMIT 1")
    suspend fun getPerfilByUserId(id: Int): Perfil?

}