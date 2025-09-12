package com.giselladomizi.first.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.giselladomizi.first.data.local.entity.Alquiler
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.giselladomizi.first.R
import com.giselladomizi.first.data.local.databese.AppDatabase
import kotlinx.coroutines.launch

class MispublicacionesActivity : AppCompatActivity() {
   private lateinit var adapter: MisPublicacionesAdapter
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


        val db = AppDatabase.getDatabase(applicationContext)
        lifecycleScope.launch {

            val alquileres = db.alquilerDAO().getAlquileresByPerfil(perfilId) //


            adapter= MisPublicacionesAdapter(alquileres.toMutableList()){ alquiler ->
                lifecycleScope.launch {
                    db.alquilerDAO().deleteAlquiler(alquiler)
                    adapter.removeItem(alquiler)
                }

            }





            publicacionesMias.adapter = adapter


        }

    }
}

