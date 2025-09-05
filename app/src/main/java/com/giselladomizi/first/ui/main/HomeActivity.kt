package com.giselladomizi.first.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.giselladomizi.first.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.giselladomizi.first.data.local.databese.AppDatabase

class HomeActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home) //Layout de Home

        val btnVerMiPerfil: ImageButton = findViewById(R.id.btnVerMiPerfil)
        val btnAddPublicacion: ImageButton = findViewById(R.id.btnAddPublicacion)
        val btnMisPublicabiones: ImageButton = findViewById(R.id.btnMisPublicabiones)

        btnVerMiPerfil.setOnClickListener{
            // Creo intent para ir a PerfilActivity
            val intentperfil = Intent(this, PerfilActivity::class.java)
            startActivity(intentperfil)
        }
        btnAddPublicacion.setOnClickListener{
            val intentaddpublicacion = Intent(this, AddpublicacionActivity::class.java)
            startActivity(intentaddpublicacion)
        }
        btnMisPublicabiones.setOnClickListener {
            val intentmispublicaciones = Intent(this, MispublicacionesActivity::class.java)
            startActivity(intentmispublicaciones)
        }

        val rvPublicaciones = findViewById<RecyclerView>(R.id.rvPublicaciones)
        rvPublicaciones.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(applicationContext)
            val alquileres = db.alquilerDAO().getAllAlquileres() //Agregamos este query
            val publicacionesConPerfil = alquileres.map { alquiler ->
                val perfil = db.perfilDAO().getPerfilById(alquiler.id_perfil)
                alquiler to perfil!!
            }
            runOnUiThread {
                rvPublicaciones.adapter = PublicacionAdapter(publicacionesConPerfil)
            }
        }

    }
}