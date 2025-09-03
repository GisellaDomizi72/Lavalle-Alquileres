package com.giselladomizi.first.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.giselladomizi.first.R

class MispublicacionesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mispublicaciones)

        val btnInicio: ImageButton = findViewById(R.id.btnInicio)
        val btnVerMiPerfil: ImageButton = findViewById(R.id.btnVerMiPerfil)
        val btnAddPublicacion: ImageButton = findViewById(R.id.btnAddPublicacion)

        btnInicio.setOnClickListener{
            val intentinicio = Intent(this, HomeActivity::class.java)
            startActivity(intentinicio)
        }
        btnVerMiPerfil.setOnClickListener{
            // Creo intent para ir a PerfilActivity
            val intentperfil = Intent(this, PerfilActivity::class.java)
            startActivity(intentperfil)
        }
        btnAddPublicacion.setOnClickListener{
            val intentaddpublicacion = Intent(this, AddpublicacionActivity::class.java)
            startActivity(intentaddpublicacion)
        }

    }
}