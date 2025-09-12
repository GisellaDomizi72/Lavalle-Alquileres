package com.giselladomizi.first.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.giselladomizi.first.R
import com.giselladomizi.first.data.local.entity.Alquiler


class MisPublicacionesAdapter (

    private val misPublicaciones: MutableList<Alquiler>,
    private val onDeleteClick: (Alquiler) -> Unit
    ) : RecyclerView.Adapter<MisPublicacionesAdapter.MisPublicacionesViewHolder>() {

        class MisPublicacionesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tipo: TextView = view.findViewById(R.id.txtTipoAlquiler)
            val ubicacion: TextView = view.findViewById(R.id.txtUbicacion)
            val descripcion: TextView = view.findViewById(R.id.txtDescripcion)
            val imagen: ImageView = view.findViewById(R.id.imgAlquiler)

            val btnEliminar: ImageButton= view.findViewById(R.id.btnEliminar)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MisPublicacionesViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_mispublicaciones, parent, false)
            return MisPublicacionesViewHolder(view)
        }

        override fun onBindViewHolder(holder: MisPublicacionesViewHolder, position: Int) {
            val alquiler = misPublicaciones[position]

            holder.tipo.text = alquiler.tipo_alqui + " - "
            holder.ubicacion.text = alquiler.ubicacion_alqui
            holder.descripcion.text = alquiler.descripcion_alqui


            if (alquiler.imagen_alqui.isNotEmpty()) {
                // Glide se encarga de manejar URIs y permisos de forma segura
                Glide.with(holder.imagen.context)
                    .load(alquiler.imagen_alqui)
                    .placeholder(android.R.color.darker_gray)
                    .into(holder.imagen)
            } else {
                holder.imagen.setImageResource(android.R.color.darker_gray)
            }

            holder.btnEliminar.setOnClickListener {
                onDeleteClick(alquiler)
            }
        }

        override fun getItemCount(): Int = misPublicaciones.size

    fun removeItem(alquiler: Alquiler){
        val position= misPublicaciones.indexOf(alquiler)
        if(position!=-1){
            misPublicaciones.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeRemoved(position,misPublicaciones.size)
        }
    }
    }
