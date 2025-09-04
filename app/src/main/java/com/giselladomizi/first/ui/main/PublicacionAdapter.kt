package com.giselladomizi.first.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.giselladomizi.first.R
import com.giselladomizi.first.data.local.entity.Alquiler
import com.giselladomizi.first.data.local.entity.Perfil

class PublicacionAdapter(
    private val publicaciones: List<Pair<Alquiler, Perfil>>
) : RecyclerView.Adapter<PublicacionAdapter.PublicacionViewHolder>() {

    class PublicacionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tipo: TextView = view.findViewById(R.id.txtTipoAlquiler)
        val ubicacion: TextView = view.findViewById(R.id.txtUbicacion)
        val descripcion: TextView = view.findViewById(R.id.txtDescripcion)
        val usuario: TextView = view.findViewById(R.id.txtUsuario)
        val telefono: TextView = view.findViewById(R.id.txtTelefono)
        val correo: TextView = view.findViewById(R.id.txtCorreo)
        val imagen: ImageView = view.findViewById(R.id.imgAlquiler)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicacionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_publicacion, parent, false)
        return PublicacionViewHolder(view)
    }

    override fun onBindViewHolder(holder: PublicacionViewHolder, position: Int) {
        val (alquiler, perfil) = publicaciones[position]

        holder.tipo.text = alquiler.tipo_alqui + " - "
        holder.ubicacion.text = alquiler.ubicacion_alqui
        holder.descripcion.text = alquiler.descripcion_alqui
        holder.usuario.text = "${perfil.nombre_p} ${perfil.apellido_p}" + " - "
        holder.telefono.text = "${perfil.telefono_p} - "
        holder.correo.text = "${perfil.correo_p}"

        if (alquiler.imagen_alqui.isNotEmpty()) {
            // Glide se encarga de manejar URIs y permisos de forma segura
            Glide.with(holder.imagen.context)
                .load(alquiler.imagen_alqui)
                .placeholder(android.R.color.darker_gray)
                .into(holder.imagen)
        } else {
            holder.imagen.setImageResource(android.R.color.darker_gray)
        }
    }

    override fun getItemCount(): Int = publicaciones.size
}
