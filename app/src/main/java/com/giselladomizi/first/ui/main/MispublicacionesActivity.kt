package com.giselladomizi.first.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.giselladomizi.first.R
import com.giselladomizi.first.data.local.databese.AppDatabase
import kotlinx.coroutines.launch

class MispublicacionesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mispublicaciones)

        val btnInicio: ImageButton = findViewById(R.id.btnInicio)
        val btnVerMiPerfil: ImageButton = findViewById(R.id.btnVerMiPerfil)
        val btnAddPublicacion: ImageButton = findViewById(R.id.btnAddPublicacion)

        btnInicio.setOnClickListener {
            val intentinicio = Intent(this, HomeActivity::class.java)
            startActivity(intentinicio)
        }
        btnVerMiPerfil.setOnClickListener {
            // Creo intent para ir a PerfilActivity
            val intentperfil = Intent(this, PerfilActivity::class.java)
            startActivity(intentperfil)
        }
        btnAddPublicacion.setOnClickListener {
            val intentaddpublicacion = Intent(this, AddpublicacionActivity::class.java)
            startActivity(intentaddpublicacion)
        }

        val prefs = getSharedPreferences("sesion", MODE_PRIVATE)
        val perfilId= prefs.getInt("id_usuario",0)

        val publicacionesMias = findViewById<RecyclerView>(R.id.publicacionesMias)
        publicacionesMias.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(applicationContext)
            val alquileres = db.alquilerDAO().getAlquileresByPerfil(perfilId) //

            val perfil = db.perfilDAO().getPerfilById(perfilId)

            val listaPublicaciones= alquileres.map{alquiler->
                alquiler to perfil!!



            }

            publicacionesMias.adapter = PublicacionAdapter(listaPublicaciones)


        }

    }
}

