package com.giselladomizi.first.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.giselladomizi.first.R
import com.giselladomizi.first.data.local.entity.Perfil


//Adaptador para mostrar los datos del usuario en un RecyclerView
class PerfilAdapter(
    private val perfiles: List<Perfil>
) : RecyclerView.Adapter<PerfilAdapter.PerfilViewHolder>() {

    class PerfilViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pNombre: TextView = view.findViewById(R.id.pNombre)
        val pCorreo: TextView = view.findViewById(R.id.pCorreo)
        val pTelefono: TextView = view.findViewById(R.id.pTelefono)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerfilViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_datoscuenta, parent, false)
        return PerfilViewHolder(view)
    }

    override fun onBindViewHolder(holder: PerfilViewHolder, position: Int) {
        val perfil = perfiles[position]
        holder.pNombre.text = "${perfil.nombre_p} ${perfil.apellido_p}"
        holder.pCorreo.text = perfil.correo_p
        holder.pTelefono.text = perfil.telefono_p
    }

    override fun getItemCount(): Int = perfiles.size
}
