package com.giselladomizi.first

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        supportActionBar?.hide() // Oculta la barra que trae por defecto

        val btnLogin: Button = findViewById(R.id.btnLogin)
        //Comentario
        btnLogin.setOnClickListener {
            Toast.makeText(this, "¡Hola! Bienvenido a Lavalle Alquileres.", Toast.LENGTH_SHORT).show()
            // Creo intent para ir a HomeActivity
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish() //cierra Login para que no se pueda volver con "atrás"
        }
    }
}