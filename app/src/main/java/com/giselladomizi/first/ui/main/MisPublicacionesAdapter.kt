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

//Adaptador para mostrar la lista de publicaciones en un RecyclerView
class MisPublicacionesAdapter (

    //Lista de publicaciones(alquileres) que se mostraran
    private val misPublicaciones: MutableList<Alquiler>,
    //Funcion lambda que se ejecuta al presionar el boton eliminar
    private val onDeleteClick: (Alquiler) -> Unit
    ) : RecyclerView.Adapter<MisPublicacionesAdapter.MisPublicacionesViewHolder>() {

    //ViewHolder: Contiene las referencias a la vista de un item
        class MisPublicacionesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tipo: TextView = view.findViewById(R.id.txtTipoAlquiler)
            val ubicacion: TextView = view.findViewById(R.id.txtUbicacion)
            val descripcion: TextView = view.findViewById(R.id.txtDescripcion)
            val imagen: ImageView = view.findViewById(R.id.imgAlquiler)

            val btnEliminar: ImageButton= view.findViewById(R.id.btnEliminar)

        }
//Infla el layout de cada item y devuelve un ViewHolder
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MisPublicacionesViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_mispublicaciones, parent, false)
            return MisPublicacionesViewHolder(view)
        }
//Asigna los datos de cada publicacion a las vistas
        override fun onBindViewHolder(holder: MisPublicacionesViewHolder, position: Int) {
            val alquiler = misPublicaciones[position]
//Setear los valores en los EditText
            holder.tipo.text = alquiler.tipo_alqui + " - "
            holder.ubicacion.text = alquiler.ubicacion_alqui
            holder.descripcion.text = alquiler.descripcion_alqui

//Si hay una imagen guardada, cargarla con Glide
            if (alquiler.imagen_alqui.isNotEmpty()) {
                // Glide se encarga de manejar URIs y permisos de forma segura
                Glide.with(holder.imagen.context)
                    .load(alquiler.imagen_alqui)
                    .placeholder(android.R.color.darker_gray)
                    .into(holder.imagen)
            } else {
                holder.imagen.setImageResource(android.R.color.darker_gray)
            }
//Accion al presionar el boton eliminar
            holder.btnEliminar.setOnClickListener {
                onDeleteClick(alquiler) //Llama a la funcion pasada desde afuera
            }
        }
//Devuelve la cantidad de publicaciones en la lista
        override fun getItemCount(): Int = misPublicaciones.size
//Elimina un item de la lista y notifica al RecyclerView para refrescar la vista
    fun removeItem(alquiler: Alquiler){
        val position= misPublicaciones.indexOf(alquiler) //Busca la posicion del alquiler
        if(position!=-1){
            misPublicaciones.removeAt(position) //Lo elimina de la lista
            notifyItemRemoved(position)  //Notifica que se elimino un item
            notifyItemRangeRemoved(position,misPublicaciones.size) //Actualiza las posiciones siguientes
        }
    }
    }
