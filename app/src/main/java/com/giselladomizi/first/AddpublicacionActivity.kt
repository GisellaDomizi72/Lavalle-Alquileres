package com.giselladomizi.first

import android.os.Bundle
import android.content.Intent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddpublicacionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_addpublicacion)

        val btnInicio: ImageButton = findViewById(R.id.btnInicio)
        val btnVerMiPerfil: ImageButton = findViewById(R.id.btnVerMiPerfil)
        val btnMisPublicabiones: ImageButton = findViewById(R.id.btnMisPublicabiones)

        btnInicio.setOnClickListener{
            val intentinicio = Intent(this, HomeActivity::class.java)
            startActivity(intentinicio)
        }
        btnVerMiPerfil.setOnClickListener{
            // Creo intent para ir a PerfilActivity
            val intentperfil = Intent(this, PerfilActivity::class.java)
            startActivity(intentperfil)
        }
        btnMisPublicabiones.setOnClickListener {
            val intentmispublicaciones = Intent(this, MispublicacionesActivity::class.java)
            startActivity(intentmispublicaciones)
        }

    }
}