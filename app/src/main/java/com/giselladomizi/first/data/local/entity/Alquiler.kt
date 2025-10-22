package com.giselladomizi.first.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "alquiler",
    foreignKeys = [
        ForeignKey(
            entity = Perfil::class,
            parentColumns = ["id_perfil"],
            childColumns = ["id_perfil"],
            onDelete = ForeignKey.CASCADE // si borro un perfil, se borran sus alquileres
        )
    ]
)

data class Alquiler(
    @PrimaryKey(autoGenerate = true) val id_alqui: Int = 0,
    val tipo_alqui: String,
    val ubicacion_alqui: String,
    val imagen_alqui: String, // ruta o url de la imagen
    val descripcion_alqui: String,
    val id_perfil: Int
)
