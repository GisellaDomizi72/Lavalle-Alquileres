package com.giselladomizi.first.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.giselladomizi.first.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.giselladomizi.first.data.local.databese.AppDatabase

class HomeActivity : AppCompatActivity(){
    private lateinit var adapter: PublicacionAdapter
    private lateinit var rvPublicaciones: RecyclerView
    private lateinit var tvSinResultados: TextView
    private lateinit var buscador: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ActivityCollector.addActivity(this)
        setContentView(R.layout.activity_home)

        // Referencias
        val btnVerMiPerfil: ImageButton = findViewById(R.id.btnVerMiPerfil)
        val btnAddPublicacion: ImageButton = findViewById(R.id.btnAddPublicacion)
        val btnMisPublicabiones: ImageButton = findViewById(R.id.btnMisPublicabiones)
        rvPublicaciones = findViewById(R.id.rvPublicaciones)
        tvSinResultados = findViewById(R.id.tvSinResultados)
        buscador = findViewById(R.id.buscador)

        rvPublicaciones.layoutManager = LinearLayoutManager(this)
        adapter = PublicacionAdapter(mutableListOf())
        rvPublicaciones.adapter = adapter

        // Cargar publicaciones iniciales
        cargarPublicaciones("")

        // Escuchar el buscador
        buscador.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                cargarPublicaciones(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Navegación del Menú
        btnVerMiPerfil.setOnClickListener {
            startActivity(Intent(this, PerfilActivity::class.java))
        }
        btnAddPublicacion.setOnClickListener {
            startActivity(Intent(this, AddpublicacionActivity::class.java))
        }
        btnMisPublicabiones.setOnClickListener {
            startActivity(Intent(this, MispublicacionesActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        // refrescar publicaciones siempre que se vuelve al Home
        cargarPublicaciones(buscador.text.toString())
    }

    private fun cargarPublicaciones(query: String) {
        val db = AppDatabase.getDatabase(applicationContext)

        lifecycleScope.launch {
            val alquileres = if (query.isEmpty()) {
                db.alquilerDAO().getAllAlquileres()
            } else {
                db.alquilerDAO().searchAlquileres(query)
            }

            val publicacionesConPerfil = alquileres.map { alquiler ->
                val perfil = db.perfilDAO().getPerfilById(alquiler.id_perfil)
                alquiler to perfil!!
            }

            if (publicacionesConPerfil.isEmpty()) {
                rvPublicaciones.visibility = View.GONE
                tvSinResultados.visibility = View.VISIBLE
            } else {
                rvPublicaciones.visibility = View.VISIBLE
                tvSinResultados.visibility = View.GONE
                adapter.updateData(publicacionesConPerfil)
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }
}