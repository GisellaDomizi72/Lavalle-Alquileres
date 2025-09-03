package com.giselladomizi.first.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey


@Entity(
    tableName = "perfil",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id_user"],
            childColumns = ["id_user"],
            onDelete = ForeignKey.CASCADE // si borro un usuario, se borran perfiles
        )
    ]
)
data class Perfil(
    @PrimaryKey(autoGenerate = true)
    val id_perfil: Int = 0,
    val nombre_p: String,
    val apellido_p: String,
    val correo_p: String,
    val telefono_p: String,
    val id_user: Int
)
