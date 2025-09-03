package com.giselladomizi.first.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario (
    @PrimaryKey(autoGenerate = true)
    val id_user: Int = 0,
    val name_u: String,
    val passw_u: String
)